package com.yifan.common.exception;

import com.yifan.common.enums.AppHttpCodeEnum;
import com.yifan.common.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@ControllerAdvice
@Component("customControllerAdvice")
@Slf4j
public class ExceptionControllerAdvice {


    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseResult exception(Exception e){
        log.error("catch exception:{}",e.getMessage());
        if (e instanceof BadCredentialsException) return ResponseResult.error(AppHttpCodeEnum.User_LOGIN_PASSWORD_ERROR);
        e.printStackTrace();
        return ResponseResult.error(AppHttpCodeEnum.SERVER_ERROR);
    }

    @ExceptionHandler({NoResourceFoundException.class})
    @ResponseBody
    public ResponseResult exception(NoResourceFoundException e){
        log.error("catch exception:{}",e.getMessage());
        return ResponseResult.error(AppHttpCodeEnum.NOT_FOUND);
    }



    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e){
        log.error("catch exception:{}",e);
        return ResponseResult.error(e.getAppHttpCodeEnum());
    }
}