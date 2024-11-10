package com.yifan.common.exception;

import com.yifan.common.enums.AppHttpCodeEnum;
import com.yifan.common.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;



@Slf4j
public class ExceptionControllerAdviceBase {


    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseResult exception(Exception e){
        log.error("catch exception:{}",e.getMessage());
        if (e instanceof BadCredentialsException) return ResponseResult.error(AppHttpCodeEnum.User_LOGIN_PASSWORD_ERROR);
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