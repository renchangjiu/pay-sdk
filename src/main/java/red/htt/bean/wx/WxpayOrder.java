package red.htt.bean.wx;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author mio
 */
@Data
@Accessors(chain = true)
public class WxpayOrder {

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。
     */
    private String id;

    /**
     * 订单名称
     */
    private String subject;

    /**
     * 商品简单描述
     */
    private String body;

    /**
     * 实付金额, 金额单位为【分】, 不能带小数
     */
    private String price;

    /**
     * 费用类型
     */
    private String feeType = "CNY";


}
