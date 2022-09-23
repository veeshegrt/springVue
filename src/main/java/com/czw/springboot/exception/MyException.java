package com.czw.springboot.exception;

import com.czw.springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyException {

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public Result handle(BaseException b){
        return Result.error(b.getCode(),b.getMessage());
    }
}
