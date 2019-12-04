package com.opay.im.controller;

import com.opay.im.common.SystemCode;
import com.opay.im.exception.ImException;
import com.opay.im.model.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultResponse handler(Exception e) {
        log.error("exception:{}", e.getMessage(), e);
        if (e instanceof MethodArgumentNotValidException) {
            StringBuilder builder = new StringBuilder();
            BindingResult result = ((MethodArgumentNotValidException) e).getBindingResult();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    builder.append(fieldError.getDefaultMessage());
                });
            }
            return new ResultResponse(SystemCode.SYS_ARG_NOT_VALID.getCode(), builder.toString(), null);
        } else if (e instanceof ImException) {
            return new ResultResponse(SystemCode.IM_ERROR.getCode(), e.getMessage(), null);
        } else {//其他未捕获异常
            return new ResultResponse(SystemCode.SYS_ERROR.getCode(), e.getMessage(), null);
        }
    }

}
