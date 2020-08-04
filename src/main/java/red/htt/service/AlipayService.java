package red.htt.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import red.htt.bean.NotifyRes;
import red.htt.bean.Result;
import red.htt.bean.ali.AlipayOrder;
import red.htt.utils.Jsons;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 电脑网站支付: 统一收单下单并支付页面接口
 *
 * @author mio
 * @see "https://opendocs.alipay.com/apis/api_1/alipay.trade.page.pay"
 */
public class AlipayService {

    /**
     * 请求 "统一收单下单并支付页面接口"
     *
     * @param order  order
     * @param config config
     * @return 自动提交的表单
     */
    public String pay(AlipayOrder order, AlipayConfig config) {
        //设置请求参数
        AlipayTradePagePayRequest ar = new AlipayTradePagePayRequest();
        ar.setReturnUrl(config.getReturnUrl());
        ar.setNotifyUrl(config.getNotifyUrl());
        ar.setBizContent(Jsons.bean2Json(order));

        AlipayClient alipayClient = this.getAlipayClient(config);
        String res = null;
        try {
            res = alipayClient.pageExecute(ar).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 支付宝同步跳转接口
     *
     * @param config  config
     * @param request request
     * @return Result<String>, String: orderId.
     */
    public Result<String> alipayReturn(AlipayConfig config, HttpServletRequest request) {
        //获取支付宝GET过来反馈信息
        Map<String, String> params = this.getParameterMap(request);

        //调用SDK验证签名
        if (signVerified(config, params)) {
            return Result.success(params.get("out_trade_no"));
        }
        return Result.error();
    }


    /**
     * 支付宝异步通知接口<br>
     * 本方法会验证 签名、商户ID、appId, 验证通过后返回订单ID, 调用方可对订单做进一步验证.<br>
     *
     * @param config  config
     * @param request request
     * @return NotifyRes
     */
    public NotifyRes notify(AlipayConfig config, HttpServletRequest request) {
        NotifyRes res = new NotifyRes();
        res.setSuccess(false);

        //获取支付宝POST过来反馈信息
        Map<String, String> params = this.getParameterMap(request);

        //调用SDK验证签名
        if (!signVerified(config, params)) {
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

    /**
     * 调用SDK验证签名
     */
    private boolean signVerified(AlipayConfig config, Map<String, String> params) {
        boolean signVerified;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, config.getAlipayPublicKey(), config.getCharset(),
                    config.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return false;
        }
        return signVerified;
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
