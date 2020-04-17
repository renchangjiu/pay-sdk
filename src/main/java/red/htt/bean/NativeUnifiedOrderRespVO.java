package red.htt.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author mio
 * @date 2020/3/10 16:49
 */
@Data
@Accessors(chain = true)
public class NativeUnifiedOrderRespVO {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误代码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 若成功, 则生成支付二维码，提供给用户进行扫码支付
     */
    private String qrCode;
}
