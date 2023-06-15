package com.foogui.foo.common.web.foo.common.core.domain;

import com.foogui.foo.common.web.foo.common.core.enums.ResponseCode;
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

    private Result() {
    }

    private Result(boolean success, int code, String message, T data, String timestamp) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public static <T> Result<T> success(T data, String message) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setCode(ResponseCode.SUCCESS.getCode());
        result.setMessage(message);
        result.setData(data);
        result.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        return result;
    }

    public static <T> Result<T> success(T data) {
        return Result.success(data, ResponseCode.SUCCESS.getMessage());
    }


    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> fail(ResponseCode responseCode) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(responseCode.getCode());
        result.setMessage(responseCode.getMessage());
        result.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        return result;
    }

    public static <T> Result<T> fail(Throwable ex, T data) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(ResponseCode.FAIL.getCode());
        result.setMessage(ex != null ? ExceptionUtils.getRootCauseMessage(ex) : ResponseCode.FAIL.getMessage());
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

    public static <T> Result<T> fail(String message) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(ResponseCode.FAIL.getCode());
        result.setMessage(message);
        result.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        result.setData(null);
        return result;
    }

}
