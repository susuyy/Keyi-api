package com.ht.note.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <h1>登录用户信息</h1>
 *@author Administrator
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfo {
    /**
     * id
     */
    private String userId;

    /**
     * 手机号
     */
    private String phoneNum;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 用户账号
     */
    private String account;

}