package com.opay.im.config;

import com.opos.feign.domain.OpayUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private com.opos.service.OpayService opayService;

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
        OpayUser opayUser = opayService.getOpayUser(request.getHeader("Authorization"));
        request.setAttribute("opayId", opayUser.getId());
        request.setAttribute("phoneNumber", opayUser.getPhoneNumber());
        request.setAttribute("opayName", opayUser.getFirstName());
        return true;
    }

}