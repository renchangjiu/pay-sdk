package red.htt.bean.wx;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author mio
 */
@Data
@Accessors(chain = true)
public class NativePayRes {

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
