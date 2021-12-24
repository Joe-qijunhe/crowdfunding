package com.joe.crowd.mvc.interceptor;

import com.joe.crowd.constant.CrowdConstant;
import com.joe.crowd.entity.Admin;
import com.joe.crowd.exception.AccessForbiddenException;
import com.sun.xml.internal.ws.handler.HandlerException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception {
        // 通过request获得session对象
        HttpSession session = httpServletRequest.getSession();

        // 从session域中取出Admin对象
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);

        // 判断admin对象是否为空，若为空表示未登录，抛出异常
        if (admin == null) {
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
        }

        // admin对象不为空，表示已登录，放行
        return true;
    }


}
