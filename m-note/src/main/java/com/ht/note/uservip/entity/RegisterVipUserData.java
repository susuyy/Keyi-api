package com.ht.note.uservip.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 前端提交参数 用户会员信息注册的实体类
 *
 * @author suyangyu
 * @since 2020-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description = "用户注册数据")
public class RegisterVipUserData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号 预留,非必填")
    private String phoneNum;

    /**
     * 微信openid
     */
    @ApiModelProperty(value = "微信openid 预留,非必填")
    private String openid;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称 非必填")
    private String nickName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像 非必填")
    private String headImg;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码 必填")
    private String password;

    /**
     * 用户登录账号
     */
    @ApiModelProperty(value = "用户登录账号 必填")
    private String account;
}
