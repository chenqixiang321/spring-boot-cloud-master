package com.opay.invite.controller;

import com.opay.invite.model.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultResponse handler(Exception e) {
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return new ResultResponse(400, "请求方法校验失败", null);
        } else if (e instanceof IllegalArgumentException) {
            return new ResultResponse(401, e.getMessage(), null);
        } else if (e instanceof MissingServletRequestParameterException) {
            return new ResultResponse(401, e.getMessage(), null);
        } else if (e instanceof NumberFormatException) {
            return new ResultResponse(401, e.getMessage(), null);
        } else {//其他未捕获异常
            log.error("exception:{}", e.getMessage(), e);
            return new ResultResponse(500, e.getMessage(), null);
        }
    }

}
