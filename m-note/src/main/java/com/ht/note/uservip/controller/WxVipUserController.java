package com.ht.note.uservip.controller;
import java.util.Date;


import com.ht.note.common.LoginUserInfo;
import com.ht.note.config.WeChatConfig;
import com.ht.note.exception.CheckException;
import com.ht.note.exception.StatusCode;
import com.ht.note.uservip.entity.*;
import com.ht.note.uservip.service.*;
import com.ht.note.uservip.service.impl.VipUserServiceImpl;

import com.ht.note.utils.TokenCreateUtil;
import com.ht.note.webauthconfig.interceptor.AccessContext;
import com.ht.note.webauthconfig.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 用户信息控制层
 *
 * @author suyangyu
 * @since 2020-06-09
 */

@Api(value = "微信版用户接口", tags = {"微信版用户接口"})
@CrossOrigin(allowCredentials = "true")
@RestController
@RequestMapping("/wx/userVip")
public class WxVipUserController {

    private Logger logger = LoggerFactory.getLogger(VipUserServiceImpl.class);

    @Autowired
    private VipUserService vipUserService;

    @Autowired
    private WeChatConfig weChatConfig;

    /**
     * 根据openid查询数据库用户信息
     * @return
     */
    @ApiOperation("根据openid查询数据库用户信息")
    @GetMapping("/getUser")
    public VipUserVO getUserByOpenId() {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        VipUser vipUser = vipUserService.getUserByOpenId(loginUserInfo.getOpenid());
        if (vipUser == null) {
            throw new CheckException(StatusCode.USER_NULL.getCode(), StatusCode.USER_NULL.getDesc());
        }
        if (StringUtils.isEmpty(vipUser.getHeadImg()) || StringUtils.isEmpty(vipUser.getNickName())){
            try {
                String accessToken = vipUserService.getAccessTokenByAppIdAndAppSecret(weChatConfig.APPID, weChatConfig.APPSECRET);
                WXUser wechatUserInfo = vipUserService.wxUserInfo(loginUserInfo.getOpenid(), accessToken);
                vipUser.setNickName(wechatUserInfo.getNickname());
                vipUser.setHeadImg(wechatUserInfo.getPictureURL());
                vipUserService.updateById(vipUser);
            } catch (Exception e) {
                logger.info("获取用户信息出错"+e.getMessage());
            }
        }
        VipUserVO vipUserVO = new VipUserVO();
        BeanUtils.copyProperties(vipUser, vipUserVO);
        return vipUserVO;
    }

    /**
     * 前端提交code码获取openid和用户信息(登录接口)
     * @param code
     * @return
     */
    @ApiOperation("前端提交code码获取openid和用户信息(登录接口)")
    @GetMapping("/requestOpenId")
    public VipUserVO getOpenid(@RequestParam("code") String code) throws Exception {
//        VipUserVO openidAndUserMsg = vipUserService.getOpenidAndUserMsg(code);

        VipUserVO openidAndUserMsg = new VipUserVO();
        openidAndUserMsg.setId(1L);
        openidAndUserMsg.setPhoneNum("15682059552");
        openidAndUserMsg.setOpenid("342582374928");
        openidAndUserMsg.setNickName("测试环境用户");
        openidAndUserMsg.setHeadImg("https://123123123123123");
        openidAndUserMsg.setCreateAt(new Date());
        openidAndUserMsg.setUpdateAt(new Date());

        String jwt = TokenCreateUtil.createJwt(openidAndUserMsg.getId()+"", openidAndUserMsg.getPhoneNum(),
                openidAndUserMsg.getOpenid(), openidAndUserMsg.getNickName(), openidAndUserMsg.getHeadImg(),openidAndUserMsg.getOpenid());
        openidAndUserMsg.setJwtToken(jwt);
        return openidAndUserMsg;
    }


    /**
     * 获取微信数据
     * @return
     */
    @ApiOperation("获取微信数据")
    @GetMapping("/getWxData")
    public Map<String,String> getWxData() {
        Map<String,String> wxData = new HashMap<>();
        wxData.put("appId", weChatConfig.APPID);
        wxData.put("appSecret", weChatConfig.APPSECRET);
        return wxData;
    }

    /**
     * 获取用户
     * @return
     */
    @ApiOperation("获取用户")
    @GetMapping("/queryUserById")
    public VipUser queryUserById(){
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        return vipUserService.getById(loginUserInfo.getUserId());
    }

    /**
     * 获取测试用户数据以及token
     * @return
     * @throws Exception
     */
    @ApiOperation("获取测试用户数据以及token")
    @GetMapping("/getTestUserToken")
    public VipUserVO getTestUserToken() throws Exception {
        VipUserVO openidAndUserMsg = new VipUserVO();
        openidAndUserMsg.setId(1L);
        openidAndUserMsg.setPhoneNum("15682059552");
        openidAndUserMsg.setOpenid("342582374928");
        openidAndUserMsg.setNickName("测试环境用户");
        openidAndUserMsg.setHeadImg("https://123123123123123");
        openidAndUserMsg.setCreateAt(new Date());
        openidAndUserMsg.setUpdateAt(new Date());

        String jwt = TokenCreateUtil.createJwt(openidAndUserMsg.getId()+"", openidAndUserMsg.getPhoneNum(),
                openidAndUserMsg.getOpenid(), openidAndUserMsg.getNickName(), openidAndUserMsg.getHeadImg(),"testuser");
        openidAndUserMsg.setJwtToken(jwt);
        return openidAndUserMsg;
    }
}

