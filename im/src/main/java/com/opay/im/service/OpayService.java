package com.opay.im.service;

import com.opos.feign.domain.OpayUser;

public interface OpayService {
    OpayUser parseToken(String token) throws Exception;
}
