package red.htt.service;


import com.github.wxpay.sdk.WXPayConfig;
import red.htt.bean.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mio
 * @see "https://pay.weixin.qq.com/wiki/doc/api/index.html"
 */
public interface WxpayService {


    /**
     * NATIVE 支付<br>
     * 请求微信统一下单接口
     *
     * @param order  order
     * @param config config
     * @return NativeUnifiedOrderRespVO
     * @see "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_1"
     */
    NativeUnifiedOrderRespVO payNative(Order order, WXPayConfig config);

    /**
     * 小程序支付
     * 请求微信统一下单接口
     *
     * @param order  order
     * @param config config
     * @param openId openId
     * @return Result
     * @see "https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_3&index=1"
     */
    Result<MinappUnifiedOrderRespVO> payMinapp(Order order, WXPayConfig config, String openId);

    /**
     * <p>微信支付结果通知</p>
     * <p>本方法会验证 签名、商户ID、appId, 验证通过后返回订单ID, 调用方可对订单做进一步验证</p>
     * <p>支付完成后，商户需要接收处理，并按文档规范返回应答</p>
     *
     * @param config  config
     * @param request request
     * @return NotifyRespVO
     * @see "https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_7&index=8pay"
     */
    NotifyRespVO notify(WXPayConfig config, HttpServletRequest request);
}
