package com.ht.note.exception;

import com.ht.note.webauthconfig.result.ResultTypeEnum;
import lombok.Data;

@Data
public class CheckException extends RuntimeException {
    int code;
    String msg;
    ResultTypeEnum resultTypeEnum;

    public CheckException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CheckException(ResultTypeEnum resultTypeEnum) {
        this.code = resultTypeEnum.getCode();
        this.msg = resultTypeEnum.getMessage();
    }



}
