package com.ht.note.uservip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.ht.note.config.NumberConstant;
import com.ht.note.config.WeChatConfig;
import com.ht.note.exception.CheckException;
import com.ht.note.exception.StatusCode;
import com.ht.note.uservip.entity.*;
import com.ht.note.uservip.mapper.VipUserMapper;
import com.ht.note.uservip.service.VipUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.note.utils.*;
import com.ht.note.webauthconfig.result.ResultTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author suyangyu
 * @since 2020-06-09
 */
@Service
public class VipUserServiceImpl extends ServiceImpl<VipUserMapper, VipUser> implements VipUserService {

    private Logger logger = LoggerFactory.getLogger(VipUserServiceImpl.class);

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private DESUtil desUtil;


    /**
     * 用户登录绑定手机号和openid
     *
     * @param registerVipUserData
     * @return
     */
    @Override
    public VipUser register(RegisterVipUserData registerVipUserData) {
        if (StringUtils.isEmpty(registerVipUserData.getOpenid())) {
            throw new CheckException(StatusCode.OPENID_ERROR.getCode(), StatusCode.OPENID_ERROR.getDesc());
        }
        VipUser vipUserQuery = this.baseMapper.selectByOpenid(registerVipUserData.getOpenid());
        if (vipUserQuery != null) {
            return vipUserQuery;
        }
        VipUser vipUser = new VipUser();
        vipUser.setOpenid(registerVipUserData.getOpenid());

        try {
            String accessToken = getAccessTokenByAppIdAndAppSecret(weChatConfig.APPID, weChatConfig.APPSECRET);
            WXUser wechatUserInfo = wxUserInfo(registerVipUserData.getOpenid(), accessToken);
            vipUser.setNickName(wechatUserInfo.getNickname());
            vipUser.setHeadImg(wechatUserInfo.getPictureURL());
        } catch (Exception e) {
            logger.info("获取用户信息出错" + e.getMessage());
            vipUser.setNickName(registerVipUserData.getNickName());
            vipUser.setHeadImg(registerVipUserData.getHeadImg());
        }

        vipUser.setVipLevel(0);
        vipUser.setPoint(0);
        vipUser.setCreateAt(new Date());
        vipUser.setUpdateAt(new Date());
        this.baseMapper.insert(vipUser);
        return vipUser;
    }

    /**
     * 根据openid查询用户信息
     *
     * @param openId
     * @return
     */
    @Override
    public VipUser getUserByOpenId(String openId) {
        VipUser vipUser = this.baseMapper.selectByOpenid(openId);
        return vipUser;
    }

    /**
     * 根据code获取用户信息
     *
     * @param code
     * @return
     */
    @Override
    public VipUserVO getOpenidAndUserMsg(String code) {
        WXData wxData = getOpenid(code);
        CacheManager.putCacheInfo(weChatConfig.TOKEN,
                new Cache(wxData.getAccessToken(), wxData.getRefreshToken(), System.currentTimeMillis(),
                        false),
                System.currentTimeMillis());
        if (StringUtils.isEmpty(wxData.getOpenid()) || "null".equals(wxData.getOpenid())) {
            throw new CheckException(StatusCode.OPENID_ERROR.getCode(), wxData.getErrMsg());
        }
        VipUser vipUser = this.baseMapper.selectByOpenid(wxData.getOpenid());
        if (vipUser == null) {
            WXUser wechatUserInfo = getWechatUserInfo(wxData.getOpenid(), wxData.getAccessToken());
            if (!StringUtils.isEmpty(wechatUserInfo.getErrCode())) {
                throw new CheckException(Integer.parseInt(wechatUserInfo.getErrCode()), wechatUserInfo.getErrMsg());
            }
            //保存用户数据
            VipUser vipUserSave = new VipUser();
            vipUserSave.setNickName(wechatUserInfo.getNickname());
            vipUserSave.setOpenid(wechatUserInfo.getOpenid());
            vipUserSave.setHeadImg(wechatUserInfo.getPictureURL());
            vipUserSave.setPoint(0);
            vipUserSave.setVipLevel(0);
            vipUserSave.setCreateAt(new Date());
            vipUserSave.setUpdateAt(new Date());
            this.baseMapper.insert(vipUserSave);
            //封装返回前端数据
            VipUserVO vipUserVO = new VipUserVO();
            BeanUtils.copyProperties(vipUserSave, vipUserVO);

            return vipUserVO;
        }
        //封装返回前端数据
        VipUserVO vipUserVO = new VipUserVO();
        BeanUtils.copyProperties(vipUser, vipUserVO);

        return vipUserVO;
    }



    /**
     * 获取用户openid
     *
     * @param code
     * @return
     */
    public WXData getOpenid(String code) {
        String content = "";
        String openId = "";
        String unionId = "";
        String accessToken = "";
        String errmsg = "";
        String errcode = "";
        String refreshToken = "";
        //封装获取openId的微信API
        StringBuffer url = new StringBuffer();
        url.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(weChatConfig.APPID)
                .append("&secret=")
                .append(weChatConfig.APPSECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpClient httpClient = new HttpClient(url.toString());
            httpClient.get();
            content = httpClient.getContent();
            System.out.println("openid信息" + openId);
            Map map = objectMapper.readValue(content, Map.class);
            System.out.println("openid信息" + map);
            openId = String.valueOf(map.get("openid"));
            accessToken = String.valueOf(map.get("access_token"));
            refreshToken = String.valueOf(map.get("refresh_token"));
            unionId = String.valueOf(map.get("unionid"));
            errcode = (String) map.get("errcode");
            errmsg = (String) map.get("errmsg");
            logger.info("获取的openID：" + openId);
        } catch (JsonParseException e) {
            log.error("json解析失败：", e);
        } catch (JsonMappingException e) {
            log.error("map转换成json失败：", e);
        } catch (Exception e) {
            log.error("http获取openId请求失败：", e);
        }
        WXData wxData = new WXData();
        wxData.setOpenid(openId);
        wxData.setAccessToken(accessToken);
        wxData.setRefreshToken(refreshToken);
        wxData.setErrCode(errcode);
        wxData.setErrMsg(errmsg);
        return wxData;
    }

    /**
     * 获取微信用户信息
     *
     * @param openId
     * @return
     */
    @Override
    public WXUser getWechatUserInfo(String openId, String accessToken) {
        logger.info("token>>" + accessToken);

        //构造获取用户基本信息api
        StringBuffer url = new StringBuffer();
        url.append("https://api.weixin.qq.com/sns/userinfo?")
                .append("access_token=").append(accessToken)
                .append("&openid=").append(openId);
        String content = "";
        ObjectMapper objectMapper = new ObjectMapper();
        WXUser wxUser = new WXUser();
        try {
            HttpClient httpClient = new HttpClient(url.toString());
            httpClient.setHttps(true);
            httpClient.get();
            content = httpClient.getContent();
            System.out.println("获取到的微信用户数据" + content);
            logger.info("获取微信用户请求响应信息:>>" + content);
            Map map = objectMapper.readValue(content, Map.class);
            System.out.println("获取到的微信用户数据" + map);
            Object mopenId = map.get("openid");
            Object nickName = map.get("nickname");
            if (openId.equals(mopenId) && nickName != null) {
                /*
                 * 获取微信用户基本信息成功，并将信息封装到平台用户对象中。
                 */
                wxUser.setNickname(String.valueOf(nickName));
                wxUser.setSex((Integer) map.get("sex"));
                wxUser.setPictureURL(String.valueOf(map.get("headimgurl")));
                wxUser.setOpenid(String.valueOf(mopenId));
                wxUser.setUnionID(String.valueOf(map.get("unionid")));
                logger.info("调用微信得到的用户信息:>>" + wxUser.getNickname() + ",photo>>" + wxUser.getPictureURL());
                return wxUser;
            }
            wxUser.setErrMsg((String) map.get("errcode"));
            wxUser.setErrCode((String) map.get("errmsg"));
            logger.info("获取openId=" + openId + "的微信用户信息失败!!");
        } catch (JsonParseException e) {
            log.error("获取微信基本用户信息时,json转换失败：>>", e);
            e.printStackTrace();
        } catch (Exception e) {
            log.error("http请求执行错误:>>", e);
            e.printStackTrace();
        }
        return wxUser;
    }

    /**
     * 获取微信用户 access_token
     *
     * @return
     */
    public String getAccessToken(String refreshToken) {
        StringBuffer url = new StringBuffer();
        url.append("https://api.weixin.qq.com/sns/oauth2/refresh_token?")
                .append("appid=").append(weChatConfig.APPID)
                .append("&grant_type=").append("refresh_token")
                .append("&refresh_token=").append(refreshToken);
        logger.info("获取全局accesss_token的请求:>>" + url.toString());
        try {
            String content;
            ObjectMapper objectMapper = new ObjectMapper();
            HttpClient httpClient = new HttpClient(url.toString());
            httpClient.setHttps(true);
            httpClient.get();
            content = httpClient.getContent();
            System.out.println(content);
            try {
                Map map = objectMapper.readValue(content, Map.class);
                Object at = map.get("access_token");
                logger.info("获取全局access_token结果为:>>" + at);
                if (null != at) {
                    logger.info("获取access_token成功!!");
                    return String.valueOf(at);
                }
            } catch (Exception e) {
                logger.error("获取全局access_token时,json转换失败：" + e.getMessage());
            }
        } catch (Exception e) {
            logger.error("获取全局access_token失败：" + e.getMessage());
        }
        return "access_token error";
    }

    /**
     * 根据appId 和 appSecret 获取 access  token
     *
     * @param appId
     * @param appSecret
     * @return
     */
    @Override
    public String getAccessTokenByAppIdAndAppSecret(String appId, String appSecret) {
        StringBuffer url = new StringBuffer();
        url.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&")
                .append("appid=").append(weChatConfig.APPID)
                .append("&secret=").append(weChatConfig.APPSECRET);
        logger.info("获取全局accesss_token的请求:>>" + url.toString());
        try {
            String content;
            ObjectMapper objectMapper = new ObjectMapper();
            HttpClient httpClient = new HttpClient(url.toString());
            httpClient.setHttps(true);
            httpClient.get();
            content = httpClient.getContent();
            System.out.println(content);
            try {
                Map map = objectMapper.readValue(content, Map.class);
                Object at = map.get("access_token");
                logger.info("获取全局access_token结果为:>>" + at);
                if (null != at) {
                    logger.info("获取access_token成功!!");
                    return String.valueOf(at);
                }
            } catch (Exception e) {
                logger.error("获取全局access_token时,json转换失败：" + e.getMessage());
            }
        } catch (Exception e) {
            logger.error("获取全局access_token失败：" + e.getMessage());
        }
        return "access_token error";
    }


    /**
     * 获取微信用户信息
     *
     * @param openId
     * @return
     */
    @Override
    public WXUser wxUserInfo(String openId, String accessToken) {
        logger.info("token>>" + accessToken);
//https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        //构造获取用户基本信息api
        StringBuffer url = new StringBuffer();
        url.append("https://api.weixin.qq.com/cgi-bin/user/info?")
                .append("access_token=").append(accessToken)
                .append("&openid=").append(openId)
                .append("&lang=zh_CN");
        String content = "";
        ObjectMapper objectMapper = new ObjectMapper();
        WXUser wxUser = new WXUser();
        try {
            HttpClient httpClient = new HttpClient(url.toString());
            httpClient.setHttps(true);
            httpClient.get();
            content = httpClient.getContent();
            System.out.println("获取到的微信用户数据" + content);
            logger.info("获取微信用户请求响应信息:>>" + content);
            Map map = objectMapper.readValue(content, Map.class);
            System.out.println("获取到的微信用户数据" + map);
            Object mopenId = map.get("openid");
            Object nickName = map.get("nickname");
            if (openId.equals(mopenId) && nickName != null) {
                /*
                 * 获取微信用户基本信息成功，并将信息封装到平台用户对象中。
                 */
                wxUser.setNickname(String.valueOf(nickName));
                wxUser.setSex((Integer) map.get("sex"));
                wxUser.setPictureURL(String.valueOf(map.get("headimgurl")));
                wxUser.setOpenid(String.valueOf(mopenId));
                wxUser.setUnionID(String.valueOf(map.get("unionid")));
                logger.info("调用微信得到的用户信息:>>" + wxUser.getNickname() + ",photo>>" + wxUser.getPictureURL());
                return wxUser;
            }
            wxUser.setErrMsg((String) map.get("errcode"));
            wxUser.setErrCode((String) map.get("errmsg"));
            logger.info("获取openId=" + openId + "的微信用户信息失败!!");
        } catch (JsonParseException e) {
            log.error("获取微信基本用户信息时,json转换失败：>>", e);
            e.printStackTrace();
        } catch (Exception e) {
            log.error("http请求执行错误:>>", e);
            e.printStackTrace();
        }
        return wxUser;
    }


    @Override
    public VipUser queryByOpenId(String openId) {
        QueryWrapper<VipUser> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("openid",openId);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public VipUser registerAccountUser(RegisterVipUserData registerVipUserData) {
        VipUser vipUserQuery = queryByAccount(registerVipUserData.getAccount());
        if (vipUserQuery!=null){
            throw new CheckException(ResultTypeEnum.ACCOUNT_EXIST);
        }

        VipUser vipUser = new VipUser();
        if (!StringGeneralUtil.checkNotNull(registerVipUserData.getNickName())){
            vipUser.setNickName(NickNameUtil.getName());
        }
        if (!StringGeneralUtil.checkNotNull(registerVipUserData.getHeadImg())){
            vipUser.setHeadImg("http://120.26.195.156:8002/20160722221059_fFJ4z_4545.jpeg");
        }
        vipUser.setAccount(registerVipUserData.getAccount());
        vipUser.setPassword(desUtil.encrypt(registerVipUserData.getPassword()));
        vipUser.setPoint(NumberConstant.user_point);
        vipUser.setVipLevel(NumberConstant.vip_level);
        save(vipUser);
        return vipUser;
    }


    @Override
    public VipUser queryByAccount(String account) {
        LambdaQueryWrapper<VipUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VipUser::getAccount,account);
        return getOne(queryWrapper);
    }

    @Override
    public VipUser accountLogin(AccountLoginData accountLoginData) {
        VipUser vipUserQuery = queryByAccount(accountLoginData.getAccount());
        if (vipUserQuery==null){
            throw new CheckException(ResultTypeEnum.ACCOUNT_PASSWORD_ERROR);
        }

        String password = vipUserQuery.getPassword();
        String decryptPassword = desUtil.decrypt(password);
        if (!decryptPassword.equals(accountLoginData.getPassword())){
            throw new CheckException(ResultTypeEnum.ACCOUNT_PASSWORD_ERROR);
        }

        return vipUserQuery;
    }

    @Override
    public VipUser loginOrRegister(AccountLoginData accountLoginData) {
        VipUser vipUserQuery = queryByAccount(accountLoginData.getAccount());
        if (vipUserQuery==null){
            //注册逻辑
            RegisterVipUserData registerVipUserData = new RegisterVipUserData();
            registerVipUserData.setAccount(accountLoginData.getAccount());
            registerVipUserData.setPassword(accountLoginData.getPassword());
            return registerAccountUser(registerVipUserData);
        }else {
            String password = vipUserQuery.getPassword();
            String decryptPassword = desUtil.decrypt(password);
            if (!decryptPassword.equals(accountLoginData.getPassword())){
                throw new CheckException(ResultTypeEnum.ACCOUNT_PASSWORD_ERROR);
            }

            return vipUserQuery;
        }


    }



}
