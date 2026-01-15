package com.ht.note.uservip.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateUserMsgData implements Serializable {

    private String headImgUrl;

    private String nickName;

    private String oriPassword;

    private String newPassword;
}
