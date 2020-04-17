package red.htt.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author mio
 * @date 2020/4/15 11:48
 */
@Data
@Accessors(chain = true)
public class NotifyRespVO {

    /**
     * 是否验证通过
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 若通过, 则有 orderId
     */
    private String orderId;

    /**
     * 若通过, 请将该参数同步返回给微信服务器(支付宝支付不需要)
     */
    private String returnRes;
}
