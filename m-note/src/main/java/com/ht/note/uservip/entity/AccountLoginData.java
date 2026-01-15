package com.ht.note.uservip.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountLoginData implements Serializable {

    /**
     * 密码
     */
    private String password;

    /**
     * 用户登录账号
     */
    private String account;

}
