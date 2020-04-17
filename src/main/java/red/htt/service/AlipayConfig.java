package red.htt.service;


/**
 * 类名：AlipayConfig
 * 功能：基础配置类
 * 详细：设置帐户有关信息及返回路径
 *
 * @author ali
 */
public abstract class AlipayConfig {

    /**
     * 获取应用ID,您的 APPID，收款账号既是您的 APPID 对应支付宝账号
     */
    public abstract String getAppId();

    /**
     * 获取商户ID, 即商户的支付宝用户号
     */
    public abstract String getUid();

    /**
     * 获取商户私钥，PKCS8格式RSA2私钥
     */
    public abstract String getMerchantPrivateKey();

    /**
     * 获取支付宝公钥, 查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
     */
    public abstract String getAlipayPublicKey();

    /**
     * 获取异步通知页面地址, 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public abstract String getNotifyUrl();

    /**
     * 获取同步跳转地址, 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public abstract String getReturnUrl();

    /**
     * 获取签名方式
     * @return signType
     */
    public final String getSignType() {
        return "RSA2";
    }

    /**
     * 获取字符编码格式
     *
     * @return charset
     */
    public final String getCharset() {
        return "utf-8";
    }

    /**
     * 获取支付宝网关
     */
    public abstract String getGatewayUrl();
}

