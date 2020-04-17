package red.htt.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.function.Consumer;

/**
 * @author mio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Result<T> {
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 状态码, 0成功/非0错误
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 携带的数据
     */
    private T data;


    public static <T> Result<T> success() {
        return new Result<T>(true, 1, "success", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(true, 1, "success", data);
    }

    public static <T> Result<T> error() {
        return new Result<T>(false, -1, "error", null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<T>(false, -1, message, null);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<T>(false, -1, message, null);
    }


    public void ifSuccess(Consumer<? super T> action) {
        if (this.getSuccess()) {
            action.accept(this.data);
        }
    }


}
