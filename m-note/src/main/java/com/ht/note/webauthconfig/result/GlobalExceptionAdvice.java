package com.ht.note.webauthconfig.result;

import com.ht.note.exception.CheckException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {


    @ExceptionHandler({CheckException.class})
    public Result checkExceptionHandler(CheckException e) {
        return new Result(e.getCode(),e.getMsg());
    }

}
