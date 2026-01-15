package com.ht.note.uservip.controller;

import com.ht.note.common.LoginUserInfo;
import com.ht.note.config.WeChatConfig;
import com.ht.note.exception.CheckException;
import com.ht.note.uservip.entity.AccountLoginData;
import com.ht.note.uservip.entity.RegisterVipUserData;
import com.ht.note.uservip.entity.UpdateUserMsgData;
import com.ht.note.uservip.entity.VipUser;
import com.ht.note.uservip.entity.VipUserVO;
import com.ht.note.uservip.service.VipUserService;
import com.ht.note.uservip.service.impl.VipUserServiceImpl;
import com.ht.note.utils.DESUtil;
import com.ht.note.utils.StringGeneralUtil;
import com.ht.note.utils.TokenCreateUtil;
import com.ht.note.webauthconfig.interceptor.AccessContext;
import com.ht.note.webauthconfig.result.ResultTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 用户信息控制层
 *
 * @author suyangyu
 * @since 2020-06-09
 */

@Api(value = "账密用户接口", tags = {"账密用户接口"})
@CrossOrigin(allowCredentials = "true")
@RestController
@RequestMapping("/account/userVip")
public class AccountUserController {

    private Logger logger = LoggerFactory.getLogger(VipUserServiceImpl.class);

    @Autowired
    private VipUserService vipUserService;

    @Autowired
    private DESUtil desUtil;

    /**
     * 用户账号密码注册
     * @param registerVipUserData
     * @return
     * @throws Exception
     */
    @ApiOperation("用户账号密码注册")
    @PostMapping("/registerAccountUser")
    public VipUserVO registerAccountUser(@RequestBody RegisterVipUserData registerVipUserData) throws Exception {

        if (!StringGeneralUtil.checkNotNull(registerVipUserData.getAccount()) || !StringGeneralUtil.checkNotNull(registerVipUserData.getPassword())){
            throw new CheckException(ResultTypeEnum.BIND_EXCEPTION);
        }

        VipUser vipUser = vipUserService.registerAccountUser(registerVipUserData);

        VipUserVO vipUserVO = new VipUserVO();
        BeanUtils.copyProperties(vipUser,vipUserVO);

        String jwt = TokenCreateUtil.createJwt(vipUser.getId() + "",
                vipUser.getPhoneNum(),
                vipUser.getOpenid(),
                vipUser.getNickName(),
                vipUser.getHeadImg(),
                vipUser.getAccount());
        vipUserVO.setJwtToken(jwt);
        return vipUserVO;
    }


    /**
     * 用户账号密码登录
     * @param accountLoginData
     * @return
     * @throws Exception
     */
    @ApiOperation("用户账号密码登录")
    @PostMapping("/accountLogin")
    public VipUserVO accountLogin(@RequestBody AccountLoginData accountLoginData) throws Exception {

        if (!StringGeneralUtil.checkNotNull(accountLoginData.getAccount()) || !StringGeneralUtil.checkNotNull(accountLoginData.getPassword())){
            throw new CheckException(ResultTypeEnum.BIND_EXCEPTION);
        }

        VipUser vipUser = vipUserService.accountLogin(accountLoginData);

        VipUserVO vipUserVO = new VipUserVO();
        BeanUtils.copyProperties(vipUser,vipUserVO);

        String jwt = TokenCreateUtil.createJwt(vipUser.getId() + "",
                vipUser.getPhoneNum(),
                vipUser.getOpenid(),
                vipUser.getNickName(),
                vipUser.getHeadImg(),
                vipUser.getAccount());

        vipUserVO.setJwtToken(jwt);
        return vipUserVO;
    }

    /**
     * 账号密码登录或注册
     * @param accountLoginData
     * @return
     * @throws Exception
     */
    @ApiOperation("账号密码登录或注册")
    @PostMapping("/loginOrRegister")
    public VipUserVO loginOrRegister(@RequestBody AccountLoginData accountLoginData) throws Exception {

        if (!StringGeneralUtil.checkNotNull(accountLoginData.getAccount()) || !StringGeneralUtil.checkNotNull(accountLoginData.getPassword())){
            throw new CheckException(ResultTypeEnum.BIND_EXCEPTION);
        }

        VipUser vipUser = vipUserService.loginOrRegister(accountLoginData);

        VipUserVO vipUserVO = new VipUserVO();
        BeanUtils.copyProperties(vipUser,vipUserVO);

        String jwt = TokenCreateUtil.createJwt(vipUser.getId() + "",
                vipUser.getPhoneNum(),
                vipUser.getOpenid(),
                vipUser.getNickName(),
                vipUser.getHeadImg(),
                vipUser.getAccount());

        vipUserVO.setJwtToken(jwt);
        return vipUserVO;
    }

    /**
     * 个人信息设置
     * @param updateUserMsgData
     * @return
     * @throws Exception
     */
    @ApiOperation("个人信息设置")
    @PostMapping("/updateUserMsg")
    public void updateUserMsg(@RequestBody UpdateUserMsgData updateUserMsgData) throws Exception {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        VipUser vipUser = vipUserService.getById(loginUserInfo.getUserId());

        if (StringGeneralUtil.checkNotNull(updateUserMsgData.getHeadImgUrl())){
            vipUser.setHeadImg(updateUserMsgData.getHeadImgUrl());
        }
        if (StringGeneralUtil.checkNotNull(updateUserMsgData.getNickName())){
            vipUser.setHeadImg(updateUserMsgData.getNickName());
        }
        if (StringGeneralUtil.checkNotNull(updateUserMsgData.getNewPassword())){
            String oriBasePassword = vipUser.getPassword();
            String decrypt = desUtil.decrypt(oriBasePassword);
            if (!decrypt.equals(updateUserMsgData.getOriPassword())){
                throw new CheckException(ResultTypeEnum.ORI_PASSWORD_ERROR);
            }
            vipUser.setPassword(desUtil.encrypt(updateUserMsgData.getNewPassword()));
        }
        vipUserService.updateById(vipUser);
    }

}

