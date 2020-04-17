package red.htt.bean;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;


/**
 * @author mio
 */
@Data
@Accessors(chain = true)
public class Order {

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
