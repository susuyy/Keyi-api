package com.ht.note.webauthconfig.result;

import lombok.Getter;

@Getter
public enum ResultTypeEnum {

    SERVICE_SUCCESS(1200, "成功"),
    SERVICE_ERROR(1500, "服务异常"),

    RESPONSE_PACK_ERROR(2100, "结果封装异常"),

    BIND_EXCEPTION(10101, "参数异常"),
    PARA_MISSING_EXCEPTION(10102, "参数不完整"),

    CODE_EXIST(11128,"code已存在"),
    ACCOUNT_EXIST(11129,"账号已被注册,请更换账号注册"),
    ACCOUNT_PASSWORD_ERROR(11130,"账号或密码错误"),
    ORI_PASSWORD_ERROR(11131,"原密码校验错误"),

    DECRYPT_ERROR(15100,"数据解密失败"),

    UPLOAD_FILE_ERROR(16001,"文件上传失败"),

    TOKEN_ERROR(40001,"鉴权失败"),
    ;


    private Integer code;
    private String message;

    ResultTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
