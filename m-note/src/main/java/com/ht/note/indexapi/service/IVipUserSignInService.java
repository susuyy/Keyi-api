package com.ht.note.indexapi.service;

import com.ht.note.indexapi.entity.VipUserSignIn;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-07-07
 */
public interface IVipUserSignInService extends IService<VipUserSignIn> {

    Integer userSignIn(String userId,String openId);

}
