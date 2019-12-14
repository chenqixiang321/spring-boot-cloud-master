package com.opay.invite.config;


import com.opos.service.OpayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.opos.feign.domain.OpayUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
        OpayUser opayUser = opayService.getOpayUser("Bearer " + request.getHeader("token"));
        request.setAttribute("opayId", opayUser.getId());
        request.setAttribute("phoneNumber", opayUser.getPhoneNumber());
        request.setAttribute("opayName", getName(opayUser));
        return true;
    }

    private String getName(OpayUser opayUser) {
        List<String> names = new ArrayList<>();
        if (StringUtils.isNotBlank(opayUser.getFirstName())) {
            names.add(opayUser.getFirstName());
        }
        if (StringUtils.isNotBlank(opayUser.getMiddleName())) {
            names.add(opayUser.getMiddleName());
        }
        if (StringUtils.isNotBlank(opayUser.getSurname())) {
            names.add(opayUser.getSurname());
        }
        if (names.isEmpty()) {
            return opayUser.getPhoneNumber();
        } else {
            return String.join(" ", names);
        }
    }

}