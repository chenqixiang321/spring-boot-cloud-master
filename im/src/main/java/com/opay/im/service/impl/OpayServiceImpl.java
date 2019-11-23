package com.opay.im.service.impl;

import com.opay.im.service.OpayService;
import com.opos.feign.domain.OpayUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("imOpayServiceImpl")
public class OpayServiceImpl implements OpayService {
    @Autowired
    private com.opos.service.OpayService opayService;

    @Override
    public OpayUser parseToken(String token) throws Exception {
        OpayUser opayUser = opayService.getOpayUser(token);
        return opayUser;
    }
}
