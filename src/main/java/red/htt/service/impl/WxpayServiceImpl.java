package red.htt.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import red.htt.bean.*;
import red.htt.service.WxpayService;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1
 *
 * @author mio
 */
public class WxpayServiceImpl implements WxpayService {

    /**
     * 生成统一下单接口的数据
     * doc: https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1#
     *
     * @param tradeType 交易类型, JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付
     */
    private Map<String, String> genUnifiedOrderData(Order order, WXPayConfig config, String tradeType) {
        Map<String, String> data = new HashMap<>();
        data.put("body", order.getBody());
        data.put("out_trade_no", order.getId());
        data.put("device_info", "");
        data.put("fee_type", order.getFeeType());
        data.put("total_fee", order.getPrice());
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", config.getNotifyUrl());
        data.put("trade_type", tradeType);
        data.put("product_id", order.getId());
        return data;
    }

    /**
     * NATIVE 支付
     * 请求微信统一下单接口
     * doc: https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1#
     */
    @Override
    public NativeUnifiedOrderRespVO payNative(Order order, WXPayConfig config) {
        NativeUnifiedOrderRespVO res = new NativeUnifiedOrderRespVO();
        res.setSuccess(false);
        try {
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = this.genUnifiedOrderData(order, config, "NATIVE");
            Map<String, String> rm = wxpay.unifiedOrder(data);
            String returnCode = rm.getOrDefault("return_code", "").toUpperCase();
            String returnMsg = rm.getOrDefault("return_msg", "");
            String resultCode = rm.getOrDefault("result_code", "").toUpperCase();
            String errCode = rm.getOrDefault("err_code", "");
            String errCodeDes = rm.getOrDefault("err_code_des", "");
            String codeUrl = rm.getOrDefault("code_url", "");
            if (!"SUCCESS".equals(returnCode)) {
                return res.setErrorMsg(returnMsg);
            }
            if (!"SUCCESS".equals(resultCode)) {
                return res.setErrorCode(errCode).setErrorMsg(errCodeDes);
            }
            return res.setSuccess(true).setQrCode(codeUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return res.setSuccess(false).setErrorMsg(e.getLocalizedMessage());
        }
    }

    /**
     * 小程序支付
     * 请求微信统一下单接口
     * doc: https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1#
     */
    @Override
    public Result<MinappUnifiedOrderRespVO> payMinapp(Order order, WXPayConfig config, String openId) {
        try {
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = this.genUnifiedOrderData(order, config, "JSAPI");
            data.put("openid", openId);
            Map<String, String> resp = wxpay.unifiedOrder(data);

            MinappUnifiedOrderRespVO respVO = new MinappUnifiedOrderRespVO();
            String timeStamp = System.currentTimeMillis() / 1000 + "";
            respVO.setTimeStamp(timeStamp);
            respVO.setNonceStr(resp.get("nonce_str"));
            respVO.setPrepayId(resp.get("prepay_id"));
            respVO.setSignType(WXPayConstants.SignType.MD5.toString());

            // 再生成小程序支付用的签名
            Map<String, String> signData = new HashMap<>(5);
            signData.put("appId", config.getAppID());
            signData.put("timeStamp", timeStamp);
            signData.put("nonceStr", respVO.getNonceStr());
            signData.put("package", "prepay_id=" + respVO.getPrepayId());
            signData.put("signType", respVO.getSignType());
            respVO.setPaySign(WXPayUtil.generateSignature(signData, config.getKey()));
            return Result.success(respVO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }


    @Override
    public NotifyRespVO notify(WXPayConfig config, HttpServletRequest request) {
        NotifyRespVO res = new NotifyRespVO();
        res.setSuccess(false);
        try {
            // 支付结果通知的xml格式数据
            String notifyData = IOUtils.toString(request.getInputStream());
            WXPay wxpay = new WXPay(config);

            // 转换成map
            Map<String, String> rm = WXPayUtil.xmlToMap(notifyData);
            boolean valid = wxpay.isPayResultNotifySignatureValid(rm);
            if (!valid) {
                return res.setErrorMsg("签名错误");
            }

            //商户订单号
            String orderId = rm.getOrDefault("out_trade_no", "");

            // 商户ID
            String mchId = rm.getOrDefault("mch_id", "");

            // appId
            String appId = rm.getOrDefault("appid", "");

            if (!config.getMchID().equals(mchId)) {
                return res.setErrorMsg("商户ID不正确");
            }

            if (!config.getAppID().equals(appId)) {
                return res.setErrorMsg("appId不正确");
            }

            // 按微信文档, 返回成功消息
            Map<String, String> retMap = new HashMap<>();
            retMap.put("return_code", "SUCCESS");
            retMap.put("return_msg", "OK");
            res.setReturnRes(WXPayUtil.mapToXml(retMap));
            return res.setSuccess(true).setOrderId(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return res;
        }
    }


}
