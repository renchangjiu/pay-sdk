package red.htt.annotation;

import java.lang.annotation.*;


/**
 * 表示参数，字段或方法的返回值永远不能为null。
 * 这是一个标记注释，没有特定的属性。
 *
 * @author mio
 * @date 2020/3/6 16:15
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE, ElementType.PACKAGE})
public @interface NonNull {
}