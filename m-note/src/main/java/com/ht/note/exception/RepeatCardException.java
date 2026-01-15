package com.ht.note.exception;

import lombok.Data;

@Data
public class RepeatCardException extends RuntimeException {
    String code;
    String msg;

    public RepeatCardException(String code,String msg) {
        this.code = code;
        this.msg = msg;
    }


}
