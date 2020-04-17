package red.htt.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author mio
 */
public class MoneyUtils {

    /**
     * 分(整型) to 元(保留两位小数)
     */
    public static String penny2yuan(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        BigDecimal ret = new BigDecimal(value);
        ret = ret.divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
        return ret.setScale(2, BigDecimal.ROUND_UP).toString();
    }

    /**
     * 元(保留两位小数) to 分(整型)
     */
    public static int yuan2penny(double yuan) {
        if (yuan == 0) {
            return 0;
        }
        BigDecimal ret = new BigDecimal(yuan + "");
        ret = ret.multiply(new BigDecimal(100));
        return ret.intValue();
    }

}
