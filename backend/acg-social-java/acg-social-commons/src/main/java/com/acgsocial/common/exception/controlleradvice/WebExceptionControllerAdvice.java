package com.acgsocial.common.exception.controlleradvice;

import com.acgsocial.common.enums.AppHttpCodeEnum;
import com.acgsocial.common.exception.ExceptionControllerAdviceBase;
import com.acgsocial.common.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@Component("WebExceptionControllerAdvice")
@ControllerAdvice
@ConditionalOnMissingBean(SecurityWebFilterChain.class)
@Slf4j
public  class WebExceptionControllerAdvice extends ExceptionControllerAdviceBase {

    @ExceptionHandler({NoResourceFoundException.class})
    @ResponseBody
    public ResponseResult exception(NoResourceFoundException e) {
        log.error("catch exception:{}", e.getMessage());
        return ResponseResult.error(AppHttpCodeEnum.NOT_FOUND);
    }
}