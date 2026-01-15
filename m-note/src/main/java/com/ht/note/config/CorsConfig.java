package com.ht.note.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsConfig {

//    /**
//     * 添加跨域处理
//     * @return
//     */
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        // 设置允许跨域请求的域名
//        config.addAllowedOrigin("*");
//        // 是否允许证书 不再默认开启
//         config.setAllowCredentials(true);
//        // 设置允许的方法
//        config.addAllowedMethod("*");
//        // 允许任何头
//        config.addAllowedHeader("*");
//        config.addExposedHeader("token");
//        config.addExposedHeader("Authorization");
//        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
//        configSource.registerCorsConfiguration("/**", config);
//        return new CorsFilter(configSource);
//    }

    @Autowired
    private ProCorsFilter myCorsFilter;

    @Bean
    public FilterRegistrationBean<ProCorsFilter> corsFilter() {
        FilterRegistrationBean<ProCorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(myCorsFilter);
        registration.addUrlPatterns("/*");
        registration.setName("corsFilter");
        registration.setOrder(1);
        return registration;
    }
}
