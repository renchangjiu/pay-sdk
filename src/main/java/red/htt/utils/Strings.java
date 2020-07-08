package red.htt.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * @author yui
 */
public final class Strings extends StringUtils {

    public static void ifEmpty(String source, Runnable r) {
        if (isEmpty(source)) {
            r.run();
        }
    }

    public static void ifNotEmpty(String source, Consumer<String> consumer) {
        if (isNotEmpty(source)) {
            consumer.accept(source);
        }
    }

    public static void ifContains(String source, String sub, Consumer<Integer> consumer) {
        if (isNotEmpty(source)) {
            int idx = source.indexOf(sub);
            if (idx != -1) {
                consumer.accept(idx);
            }
        }
    }


    public static String htmlSpecialCharsEncode(String str) {
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        return str;
    }

    /**
     * <p>单词首字母转大写</p>
     *
     * <pre>
     * StringUtil.firstLetterToUppercase(null)      = null
     * StringUtil.firstLetterToUppercase("")        = ""
     * StringUtil.firstLetterToUppercase(" ")       = " "
     * StringUtil.firstLetterToUppercase("bob")     = "Bob"
     * StringUtil.firstLetterToUppercase("1 bob  ") = "1  bob  "
     * </pre>
     */
    public static String firstLetterToUpper(String str) {
        return (str != null && str.length() >= 1) ? Character.toUpperCase(str.charAt(0)) + str.substring(1) : str;

    }

    private static final Pattern PATTERN = Pattern.compile("\\+");

    public static String encodeUri(String source) {
        if (isEmpty(source)) {
            return "";
        }
        try {
            String res = URLEncoder.encode(source, StandardCharsets.UTF_8.displayName());
            return PATTERN.matcher(res).replaceAll("%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String decodeUri(String source) {
        try {
            return isEmpty(source) ? "" : URLDecoder.decode(source, StandardCharsets.UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String toStr(Object o) {
        return o == null ? "" : o.toString();
    }

    public static <T> List<T> trans(Object list) {
        return (List<T>) list;
    }

    public static String[] split(String str, String sep) {
        if (isEmpty(str)) {
            return new String[0];
        }
        return Arrays.stream(str.split(sep))
                .filter(StringUtils::isNotEmpty)
                .toArray(String[]::new);

    }
}
