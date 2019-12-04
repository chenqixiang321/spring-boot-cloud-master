package com.opay.im.config;

import com.opay.im.service.OpayService;
import com.opos.feign.domain.OpayUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private OpayService opayService;

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        System.out.println("getContextPath:" + request.getContextPath());
        System.out.println("getServletPath:" + request.getServletPath());
        System.out.println("getRequestURI:" + request.getRequestURI());
        System.out.println("getRequestURL:" + request.getRequestURL());
        System.out.println("getRealPath:" + request.getSession().getServletContext().getRealPath("image"));
        System.out.println("Authorization:" + request.getHeader("Authorization"));
        OpayUser opayUser = opayService.parseToken(request.getHeader("Authorization"));
        request.setAttribute("opayId", opayUser.getId());
        request.setAttribute("phoneNumber", opayUser.getPhoneNumber());
        request.setAttribute("opayName", opayUser.getFirstName());
//        request.setAttribute("opayId", "1");
//        request.setAttribute("phoneNumber", "12345678");
//        request.setAttribute("opayName", "haha");
        return true;
    }

}