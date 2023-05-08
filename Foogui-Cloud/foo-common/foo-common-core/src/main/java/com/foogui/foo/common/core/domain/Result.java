package com.foogui.foo.common.core.domain;

import com.foogui.foo.common.core.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 响应对象
 *
 * @author Foogui
 * @date 2023/05/02
 */
@Getter
@Setter
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 906941499553860342L;

    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";


    private Boolean success;

    private int code;

    private String message;

    private T data;

    private String timestamp;

    public static <T>  Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setCode(ResponseCode.SUCCESS.getCode());
        result.setMessage(ResponseCode.SUCCESS.getDesc());
        result.setData(data);
        result.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        return result;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> fail(ResponseCode responseCode) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(responseCode.getCode());
        result.setMessage(responseCode.getDesc());
        result.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        return result;
    }

    public static <T> Result<T> fail(Throwable ex, T data) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(ResponseCode.ERROR.getCode());
        result.setMessage(ex != null ? ExceptionUtils.getRootCauseMessage(ex) : ResponseCode.ERROR.getDesc());
        result.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(Throwable ex) {
        return fail(ex, null);
    }

    public static <T> Result<T> fail() {
        return fail(null, null);
    }
    public static <T> Result<T> fail(T message) {
        return fail(null, message);
    }
}
