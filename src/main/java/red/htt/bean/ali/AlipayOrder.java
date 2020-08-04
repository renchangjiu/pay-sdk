package red.htt.bean.ali;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 详见文档 "请求参数" 部分
 * 注释格式: 是否必填	最大长度	描述
 *
 * @author mio
 */
@Data
@Accessors(chain = true)
public class AlipayOrder {

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
