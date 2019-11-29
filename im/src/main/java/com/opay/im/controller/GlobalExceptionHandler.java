package com.opay.im.controller;

import com.opay.im.model.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultResponse handler(Exception e) {
        log.error("exception:{}", e.getMessage(), e);
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return new ResultResponse(400, "请求方法校验失败", null);
        } else if (e instanceof IllegalArgumentException) {
            return new ResultResponse(401, e.getMessage(), null);
        } else if (e instanceof MissingServletRequestParameterException) {
            return new ResultResponse(401, e.getMessage(), null);
        } else if (e instanceof NumberFormatException) {
            return new ResultResponse(401, e.getMessage(), null);
        } else if (e instanceof MethodArgumentNotValidException) {
            StringBuilder builder = new StringBuilder();
            BindingResult result = ((MethodArgumentNotValidException) e).getBindingResult();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    builder.append(fieldError.getDefaultMessage());
                });
            }
            return new ResultResponse(400, builder.toString(), null);
        } else {//其他未捕获异常
            return new ResultResponse(500, e.getMessage(), null);
        }
    }

}
