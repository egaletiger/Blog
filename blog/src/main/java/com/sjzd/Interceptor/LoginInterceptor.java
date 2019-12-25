package com.sjzd.Interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对所有访问后台的请求进行拦截认证
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (null == request.getSession().getAttribute("user")) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
