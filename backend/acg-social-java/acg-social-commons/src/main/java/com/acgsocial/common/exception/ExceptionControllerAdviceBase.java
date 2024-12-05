package com.acgsocial.common.exception;

import com.acgsocial.common.enums.AppHttpCodeEnum;
import com.acgsocial.common.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
public class ExceptionControllerAdviceBase {


    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseResult exception(Exception e){
        log.error("catch exception:{}",e.getMessage());
        if (e instanceof HttpRequestMethodNotSupportedException) return ResponseResult.error(AppHttpCodeEnum.METHOD_NOT_ALLOWED);
        e.printStackTrace();
        return ResponseResult.error(AppHttpCodeEnum.SERVER_ERROR);
    }



    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e){
        log.error("catch exception:{}",e);
        return ResponseResult.error(e.getAppHttpCodeEnum());
    }

}