package red.htt.bean;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author mio
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MinappUnifiedOrderRespVO {
    /**
     * 时间戳从1970年1月1日00:00:00至今的秒数,即当前的时间
     */
    private String timeStamp;

    /**
     * 随机字符串，长度为32个字符以下。
     */
    private String nonceStr;

    /**
     * 统一下单接口返回的 prepay_id 参数值
     */
    private String prepayId;

    /**
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5。注意此处需与统一下单的签名类型一致
     */
    private String signType;

    /**
     * 签名, doc: https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=3
     */
    private String paySign;
}
