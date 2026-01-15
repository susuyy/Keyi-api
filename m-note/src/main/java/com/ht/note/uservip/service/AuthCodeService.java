package com.ht.note.uservip.service;

import com.ht.note.uservip.entity.AuthCode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 * 记录手机号验证码 服务类
 * </p>
 *
 * @author ${author}
 * @since 2020-06-16
 */
public interface AuthCodeService extends IService<AuthCode> {


    /**
     * 查询验证码
     *
     * @param authKey
     * @return
     */
    AuthCode queryByAuthKey(String authKey);

    /**
     * 更新验证码
     * @param authKey
     * @param code
     * @param validityDate
     */
    void updateByAuthKey(String authKey, String code, Date validityDate);

}
