package com.ht.note.webauthconfig;

import com.ht.note.webauthconfig.interceptor.LoginUserInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginUserInfoInterceptor loginUserInfoInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
        InterceptorRegistration registration = registry.addInterceptor(loginUserInfoInterceptor);
        registration.addPathPatterns("/**");
        //swagger相关放行
        registration.excludePathPatterns("/swagger-resources/**", "/webjars/**","/doc.html/**", "/v2/**", "/swagger-ui.html/**","/","/csrf");
        //子域名代理放行
        registration.excludePathPatterns("/keyi-note/swagger-resources/**", "/keyi-note/webjars/**","/keyi-note/doc.html/**", "/keyi-note/v2/**", "/keyi-note/swagger-ui.html/**","/keyi-note/","/keyi-note/csrf");
    }

}
