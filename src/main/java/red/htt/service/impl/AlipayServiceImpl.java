package red.htt.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.Data;
import lombok.experimental.Accessors;
import red.htt.bean.NotifyRespVO;
import red.htt.bean.Order;
import red.htt.service.AlipayConfig;
import red.htt.service.AlipayService;
import red.htt.utils.JsonUtils;
import red.htt.utils.MoneyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 电脑网站支付: 统一收单下单并支付页面接口
 *
 * @author mio
 * @see "https://opendocs.alipay.com/apis/api_1/alipay.trade.page.pay"
 */
public class AlipayServiceImpl implements AlipayService {

    @Override
    public String pay(BizContent bc, AlipayConfig config) {
        new HashSet<>().add("d");
        //设置请求参数
        AlipayTradePagePayRequest ar = new AlipayTradePagePayRequest();
        ar.setReturnUrl(config.getReturnUrl());
        ar.setNotifyUrl(config.getNotifyUrl());
        ar.setBizContent(JsonUtils.bean2Json(bc));

        AlipayClient alipayClient = this.getAlipayClient(config);
        String res = null;
        try {
            res = alipayClient.pageExecute(ar).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Pair<Boolean, String> alipayReturn(AlipayConfig config, HttpServletRequest request) {
        //获取支付宝GET过来反馈信息
        Map<String, String> params = this.getParameterMap(request);

        //调用SDK验证签名
        if (!signVerified(config, params)) {
            return Pair.of(false, null);
        }
        return Pair.of(true, params.get("out_trade_no"));
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

    /**
     * 详见文档 "请求参数" 部分
     * 注释格式: 是否必填	最大长度	描述
     */
    @Data
    @Accessors(chain = true)
    public static class BizContent {

        /**
         * 必选	64	商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
         */
        private String out_trade_no;

        /**
         * 必选	64	销售产品码，与支付宝签约的产品码名称。
         * 注：目前仅支持FAST_INSTANT_TRADE_PAY
         */
        private final String product_code = "FAST_INSTANT_TRADE_PAY";

        /**
         * 必选	11	订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。
         */
        private String total_amount;

        /**
         * 必选	256	订单标题
         */
        private String subject;

        /**
         * 可选	128	订单描述
         */
        private String body;

        /**
         * 可选	32	绝对超时时间，格式为yyyy-MM-dd HH:mm:ss
         */
        private String time_expire;

        /**
         * 可选	512	公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。
         */
        private String passback_params;
    }
}
