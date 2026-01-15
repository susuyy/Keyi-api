package com.ht.note.webauthconfig.interceptor;



import com.ht.note.common.LoginUserInfo;
import com.ht.note.exception.CheckException;
import com.ht.note.utils.TokenParseUtil;
import com.ht.note.webauthconfig.CommonConstant;
import com.ht.note.webauthconfig.HeadersConstant;
import com.ht.note.webauthconfig.result.ResultTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h1>用户身份统一登录拦截</h1>
 * */
@SuppressWarnings("all")
@Slf4j
@Component
public class LoginUserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        log.info("请求路径是:============="+request.getRequestURI());
        // 部分请求不需要带有身份信息, 即白名单
        if (checkWhiteListUrl(request.getRequestURI())) {
            return true;
        }
        // 先尝试从 http header 里面拿到 token
        String token = request.getHeader(HeadersConstant.TOKEN_HEADER);

        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        } catch (Exception ex) {

            log.error("parse login user info error: [{}]", ex.getMessage(), ex);
        }

        // 如果程序走到这里, 说明 header 中没有 token 信息
        if (null == loginUserInfo) {
            throw new CheckException(ResultTypeEnum.TOKEN_ERROR);
        }

        log.info("set login user info: [{}]", request.getRequestURI());
        // 设置当前请求上下文, 把用户信息填充进去
        AccessContext.setLoginUserInfo(loginUserInfo);

        return true;
    }

    /**
     * 请求之后，返回之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * <h2>在请求完全结束后调用, 常用于清理资源等工作</h2>
     * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

        if (null != AccessContext.getLoginUserInfo()) {
            AccessContext.clearLoginUserInfo();
        }
    }

    /**
     * <h2>校验是否是白名单接口</h2>
     * swagger2 接口
     * */
    private boolean checkWhiteListUrl(String url) {

        return StringUtils.containsAny(
                url,
                "springfox", "swagger", "/v2/api-docs",
                "webjars", "doc.html","index.html","swagger-ui.html",
                "/wx/userVip/requestOpenId","/wx/userVip/getTestUserToken",
                "/account/userVip/registerAccountUser", "/account/userVip/accountLogin","/account/userVip/loginOrRegister",
                "/file"
        );
    }
}