package com.ht.note.uservip.service;

import com.ht.note.uservip.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author suyangyu
 * @since 2020-06-09
 */
public interface VipUserService extends IService<VipUser> {


    /**
     * 用户绑定手机号和openid
     *
     * @param registerVipUserData
     * @return
     */
    VipUser register(RegisterVipUserData registerVipUserData);

    /**
     * 根据openid查询用户信息
     *
     * @param openId
     * @return
     */
    VipUser getUserByOpenId(String openId);

    /**
     * 根据code获取用户信息 和openid
     * @param code
     * @return
     */
    VipUserVO getOpenidAndUserMsg(String code);

    WXUser getWechatUserInfo(String openId, String accessToken);

    String getAccessTokenByAppIdAndAppSecret(String appId, String appSecret);

    WXUser wxUserInfo(String openId, String accessToken);

    VipUser queryByOpenId(String openId);

    /**
     * 注册账号密码登录用户
     * @param registerVipUserData
     * @return
     */
    VipUser registerAccountUser(RegisterVipUserData registerVipUserData);

    VipUser queryByAccount(String account);

    /**
     * 用户账号密码登录
     * @param accountLoginData
     * @return
     */
    VipUser accountLogin(AccountLoginData accountLoginData);

    /**
     * 登录或注册
     * @param accountLoginData
     * @return
     */
    VipUser loginOrRegister(AccountLoginData accountLoginData);

}
