package red.htt.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import red.htt.bean.NotifyRespVO;
import red.htt.bean.Order;
import red.htt.service.AlipayConfig;
import red.htt.service.AlipayService;
import red.htt.utils.MoneyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mio
 * @apiNote https://opendocs.alipay.com/apis/api_1
 * @date 2019/10/8 9:22
 */
public class AlipayServiceImpl implements AlipayService {

    @Override
    public String pay(Order order, AlipayConfig config) {
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(config.getReturnUrl());
        alipayRequest.setNotifyUrl(config.getNotifyUrl());

        //商户订单号, 商户网站订单系统中唯一订单号, 必填
        String out_trade_no = order.getId();

        //付款金额(元), 精确到小数点后两位, 必填
        String total_amount = MoneyUtils.penny2yuan(order.getPrice());

        //订单名称, 必填
        String subject = StringUtils.substring(order.getSubject(), 0, 10).trim();

        //商品描述, 可空
        String body = order.getBody();

        // 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）和异步通知时
        // 将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。
        String passback_params = "haha";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"passback_params\":\"" + passback_params + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        AlipayClient alipayClient = this.getAlipayClient(config);
        String res = null;
        try {
            res = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 同步跳转接口 (仅做签名验证)
     */
    @Override
    public Pair<Boolean, String> alipayReturn(AlipayConfig config, HttpServletRequest request) {
        try {
            //获取支付宝GET过来反馈信息
            Map<String, String> params = this.getParameterMap(request);

            //调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, config.getAlipayPublicKey(), config.getCharset(),
                    config.getSignType());
            if (signVerified) {
                return Pair.of(true, params.get("out_trade_no"));
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return Pair.of(false, null);
    }

    /**
     * 支付宝异步通知接口
     */
    @Override
    public NotifyRespVO notify(AlipayConfig config, HttpServletRequest request) {
        NotifyRespVO res = new NotifyRespVO();
        res.setSuccess(false);

        //获取支付宝POST过来反馈信息
        Map<String, String> params = this.getParameterMap(request);

        //调用SDK验证签名
        boolean signVerified;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, config.getAlipayPublicKey(), config.getCharset(),
                    config.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return res.setErrorMsg("签名错误");
        }

        if (!signVerified) {
            return res.setErrorMsg("签名错误");
        }

        String orderId = request.getParameter("out_trade_no");

        //交易状态
        String tradeStatus = request.getParameter("trade_status");

        // 商户ID, 即UID, 即商户支付宝用户号
        String sellerId = request.getParameter("seller_id");

        String appId = request.getParameter("app_id");

        if (!config.getUid().equals(sellerId)) {
            return res.setErrorMsg("商户ID不正确");
        }

        if (!config.getAppId().equals(appId)) {
            return res.setErrorMsg("appId不正确");
        }

        if ("TRADE_SUCCESS".equals(tradeStatus)) {
            return res.setSuccess(true).setOrderId(orderId);
        } else {
            return res.setErrorMsg("交易状态错误");
        }
    }

    private AlipayClient getAlipayClient(AlipayConfig config) {
        return new DefaultAlipayClient(config.getGatewayUrl()
                , config.getAppId()
                , config.getMerchantPrivateKey()
                , "json"
                , config.getCharset()
                , config.getAlipayPublicKey()
                , config.getSignType());
    }


    private Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决, 这段代码在出现乱码时使用
            // valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            params.put(name, valueStr);
        }
        return params;
    }

}
