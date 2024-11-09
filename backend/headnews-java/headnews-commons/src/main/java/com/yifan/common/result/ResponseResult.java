package com.yifan.common.result;

import com.yifan.common.enum_.AppHttpCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ResponseResult<T> implements Serializable {


    private Integer code;

    private String errorMessage;

    private T data;



    private ResponseResult<T> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.errorMessage = msg;
        return this;
    }

    public static ResponseResult<?> set(int code, String msg) {
        return new ResponseResult<>(code, msg, null);
    }


    private static ResponseResult<?> set(AppHttpCodeEnum enums, Object data) {
        return new ResponseResult<>(
                        enums.getCode(),
                        enums.getMessage(),
                        data
                    );
    }

    public static ResponseResult<?> success(Object data) {
        return new ResponseResult<>(
                AppHttpCodeEnum.SUCCESS.getCode(),
                AppHttpCodeEnum.SUCCESS.getMessage(),
                data
        );
    }


    public static ResponseResult<?> error(AppHttpCodeEnum enums){
        return new ResponseResult<>(
                        enums.getCode(),
                        enums.getMessage(),
                null);
    }


}