package com.ht.note.uservip.entity;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 响应前端的用户会员数据实体类
 *
 * @author suyangyu
 * @since 2020-06-09
 */
@Data
public class VipUserVO implements Serializable {


    private static final long serialVersionUID = 1L;


    private Long id;

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
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date updateAt;

    /**
     * 返回的jwtToken
     */
    private String jwtToken;
}
