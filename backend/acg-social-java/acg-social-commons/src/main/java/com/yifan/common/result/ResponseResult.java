package com.yifan.common.result;

import com.yifan.common.enums.AppHttpCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Generic class for wrapping API responses.
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ResponseResult implements Serializable {

    /**
     * The HTTP response code.
     */
    private Integer code;

    /**
     * The message, if any.
     */
    private String message;

    /**
     * The response data.
     */
    private Object data;

    private boolean success = true;;



    /**
     * Creates a successful ResponseResult with the given parameters.
     *
     * @param enums the AppHttpCodeEnum instance
     * @param message the success message
     * @param data the response data
     * @return a new ResponseResult instance
     */
    private static ResponseResult success(AppHttpCodeEnum enums, String message, Object data) {
        return new ResponseResult(
                enums.getCode(),
                message,
                data,
                true
        );
    }

    /**
     * Creates a successful ResponseResult with the given data.
     *
     * @param data the response data
     * @return a new ResponseResult instance
     */
    public static ResponseResult success(Object data) {
        return new ResponseResult(
                AppHttpCodeEnum.SUCCESS.getCode(),
                AppHttpCodeEnum.SUCCESS.getMessage(),
                data,
                true
        );
    }

    /**
     * Creates an error ResponseResult with the given AppHttpCodeEnum.
     *
     * @param enums the AppHttpCodeEnum instance
     * @return a new ResponseResult instance
     */
    public static ResponseResult error(AppHttpCodeEnum enums) {
        return new ResponseResult(
                enums.getCode(),
                enums.getMessage(),
                null,
                false
        );
    }
}