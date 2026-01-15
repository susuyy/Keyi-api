package com.ht.note.uservip.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class WXData implements Serializable {

    private String accessToken;

    private String openid;

    private String errCode;

    private String errMsg;

    private String refreshToken;
}
