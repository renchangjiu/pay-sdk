package red.htt;

import red.htt.service.AlipayConfig;

/**
 * 支付宝开放平台提供的测试账户
 *
 * @author mio
 * @see "https://openhome.alipay.com/platform/appDaily.htm?tab=info"
 */
public class AlipayConfigImpl extends AlipayConfig {

    @Override
    public String getAppId() {
        return "2016092800616725";
    }

    @Override
    public String getUid() {
        return "2088102177668165";
    }

    @Override
    public String getMerchantPrivateKey() {
        return "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC1Mt9mFwRmGypU1jnDXEnX7kJo2MG/N8jurAPthMEpY9FUDas7gxnj9zNM1kBaZ14jXcUlftbicUV3UchqA0IE6l982/WKUQcjhjfw/Ztu5xEmf517Zf2tqeQCLk2U4Rc9Jx89Lfh7dXfsJZ1uI6iwStebQtf18A4L+dfSjOROBUAvIuQMdCTx8F0dqj6E5shV8whxkACzEsjoHQMtoFIRLMv3fvCkaG8vUD+c0CaSyf0UeWsYNIVAQGnFpvkhwZrebxxx6zCUwnITju57szZ05kLne7/DEwz6kyHRJCqpeiGNa1LCt6XdX9hyYSEWS3VBJTVThsvl2P3p22jZFnQLAgMBAAECggEBAIWYvJ3I2rVt0gg66tvfb5KFWPF032Tc/Ij8yuiUuNaLggs3CMkYID60RF6imVAOZposyO6cVlMesNkIs3t2a3a0VzL3+lHA7EKE8zn7wl5Cf6Ki9LnGuQMR/8qb2Rer3jZ4Yc/51Psx4Pl7wlcyc22HwO/4mMH7/F9YFohm2O2sM8yyk+tbmYKgP1T0vr5DRgQyzt1PfAvLFb/5ZlS5L6G3eyMByGtGEEAUMRCABv9TytwWEtc6lB6isvGBZGeJzdWzZnExENq9Gj7qlYlFmsVeCOS05ApNkvo0mURpkdo/WIGfLA2cEtYOYV6t7ElelZtv7Q5aMWSu0lS59tCH5AECgYEA2KEsLR3aaaFrovwZC8zsLDAVbmSKkv7W0NHpCgax9rx1lQoryNZ+sCmwkklq233doVfMmZ4oHCnLfqqtbavvwZ3euNmkBrz+MNr6M11Dnnc4oLl2449LAP+tMslnK2FvujMGnhYKjneB4Pjz0oBI2jShy9Z+kvK+8Rch6Iw3ZBkCgYEA1iFB0OHNlYRt/7LA5yyb/GevDDunCC0+19Dzr3+Z55CGPDY95yRMx3db8PIpJyS3v4KE5euRGO7VdgSoY+ky4TkZDksvFBRi/80RvY6Gda+6yCQylrJU8itYb+o5UafoOkBUMDLn6w2IOghvSIdfWPYa7sDO66WfWnO4FW/NfcMCgYBhCqIte8gRtZvwIRpfLOdHTCrtjuB8xOgaF05bjXA/ZWEO6MWAbIXICITuozDZLmMQGbKvxieVpitgjLiv/muiwkO5btWJpzP/UrjuNYNLA4E8jphxjAk/Y0mFDK/IwRc5xA3c12bXNzXZP+yJiaoZBabNF0MQNOnQOlqYP+e4UQKBgG0q32ci97D2py3pcDdrosr4AWJ3Nx0QFC3rNZGPuGM6Z3VjIgQpmYtH6vPYQerW6WcVn7OoeX0ApotYrJDjXzE77u4+nm3aRu6rGhROCXcOdGd9LN1vZadhOeE0xKxAskCgc0tvaJWJfJ45lqKfgZjVP73hRp0U/z0aKgujyFwJAoGAAi0ZPuBbfazLJk9fgAwjhQrivGSceXm7PuCZ1rhSXRt9auOwp4F9gmvNPqQt37Z87/q1AHw6a0P9tP7BwXaBfI4m7DCXk3h4sjOFyiKoCPNrD356UndJ8UbAPxsaG04cpSL/k2Xlk6HdGQ8ZpqhXwmT3X5+NXQAcaF77n1OU7Gc=";
    }

    @Override
    public String getAlipayPublicKey() {
        return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoZe3+fCRdyl6rUIZuYqRvm1IDjZVacy0ePeFyV6JNRrRazosIyAcRM37zfcH9r0Qioda4kxnzLWQR3Kkf2Ep5TXM9eIV3ToKD6FEh2E3mz32YEfrSC4ag5yTPbF8SvdyDHrZgTnF2yFFaGiBknRXnbkObivRirBVq44Zue5DDjW6/bGeSGRLVf4feQwxvhtED2BAnPzhDlle6dWEDV1hOKoO4KNAXhLW46KYz8SleRqw6xy881utUeWwyribv5REvKSkkLTRk7GgbJdqR4mD4KcfgNvxZcLJzySayzhhrLVC+9fchtVOXDg5BlvbScj+iSUnBI/KWUuOtJwRHGIpDwIDAQAB";
    }

    @Override
    public String getNotifyUrl() {
        return "http://nhn83h.natappfree.cc/comingnet-gzcxxt/pay/alipay/notify.shtml";
    }

    @Override
    public String getReturnUrl() {
        return "http://nhn83h.natappfree.cc/comingnet-gzcxxt/pay/alipay/return.shtml";
    }

    @Override
    public String getGatewayUrl() {
        return "https://openapi.alipaydev.com/gateway.do";
    }
}
