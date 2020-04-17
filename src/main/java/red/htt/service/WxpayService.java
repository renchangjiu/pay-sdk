package red.htt.service;


import com.github.wxpay.sdk.WXPayConfig;
import red.htt.bean.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mio
 * @apiNote https://pay.weixin.qq.com/wiki/doc/api/index.html
 * @date 2019/10/8 9:22
 */
public interface WxpayService {


    /**
     * NATIVE 支付
     * 请求微信统一下单接口
     *
     * @apiNote https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_1
     */
    NativeUnifiedOrderRespVO payNative(Order order, WXPayConfig config);

    /**
     * 小程序支付
     * 请求微信统一下单接口
     *
     * @apiNote https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_3&index=1
     */
    Result<MinappUnifiedOrderRespVO> payMinapp(Order order, WXPayConfig config, String openId);

    /**
     * 微信支付结果通知.<br/>
     * 本方法会验证 签名、商户ID、appId, 验证通过后返回订单ID, 调用方可对订单做进一步验证.<br/>
     * 支付完成后，商户需要接收处理，并按文档规范返回应答.<br/>
     *
     * @param config  config
     * @param request request
     * @return NotifyRespVO
     * @apiNote https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_7&index=8<br/>
     */
    NotifyRespVO notify(WXPayConfig config, HttpServletRequest request);
}
