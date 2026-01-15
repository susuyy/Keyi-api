package com.ht.note.utils;

import com.alibaba.fastjson.JSON;
import com.ht.note.common.LoginUserInfo;
import com.ht.note.webauthconfig.CommonConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sun.misc.BASE64Decoder;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;

/**
 * <h1>JWT Token 解析工具类</h1>
 * */
public class TokenCreateUtil {

    public static String createJwt(String userId,String phoneNum,String openid,String nickName,String headImg,String account) throws Exception {
        // Token 中塞入对象, 即 JWT 中存储的信息, 后端拿到这些信息就可以知道是哪个用户在操作
        LoginUserInfo loginUserInfo = new LoginUserInfo(userId,phoneNum,openid,nickName,headImg,account);
        int expire = CommonConstant.DEFAULT_EXPIRE_DAY;
        // 计算超时时间
        ZonedDateTime zdt = LocalDate.now().plus(expire, ChronoUnit.DAYS)
                .atStartOfDay(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());

        return Jwts.builder()
                // jwt payload --> KV
                .claim(CommonConstant.JWT_DATA_MAP_KEY, JSON.toJSONString(loginUserInfo))
                // jwt id
                .setId(UUID.randomUUID().toString())
                // jwt 过期时间
                .setExpiration(expireDate)
                // jwt 签名 --> 加密
                .signWith( SignatureAlgorithm.RS256,getPrivateKey())
                .compact();
    }

    /**
     * <h2>根据本地存储的私钥获取到 PrivateKey 对象</h2>
     * */
    private static PrivateKey getPrivateKey() throws Exception {
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(CommonConstant.PRIVATE_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(priPKCS8);
    }


    public static void main(String[] args) throws Exception {
        LoginUserInfo loginUserInfoSet = new LoginUserInfo();

        loginUserInfoSet.setPhoneNum("15682059552");

        loginUserInfoSet.setNickName("ceshi");
        loginUserInfoSet.setHeadImg("sdfhkajdfhjkasdf");

        String myuser = createJwt(loginUserInfoSet.getUserId(),
                loginUserInfoSet.getPhoneNum(),loginUserInfoSet.getOpenid(),
                loginUserInfoSet.getOpenid(),loginUserInfoSet.getHeadImg(),loginUserInfoSet.getAccount());

        System.out.println(myuser);
        LoginUserInfo loginUserInfo = TokenParseUtil.parseUserInfoFromToken(myuser);

        System.out.println(loginUserInfo);
    }


}