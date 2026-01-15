package com.ht.note.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 微信参数
 *
 * @author suyangyu
 * @since 2020-06-09
 */
@Configuration
public class WeChatConfig {

    /**
     * APP ID       public static final String APPID = "wx7bc28f9b428822cc";
     */
    @Value("${wx.appId}")
    public String APPID;

    /**
     * APP Secret      public static String APPSECRET = "4c9d069c4f2544bf720cc444c6826cf9";
     */
    @Value("${wx.appSecret}")
    public String APPSECRET;


    public static final String TOKEN = "token";


}
