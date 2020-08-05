package com.opay.invite.backstage.service;

import com.opay.invite.backstage.exception.BackstageException;
import com.opay.invite.backstage.service.dto.LoginReqDto;
import com.opay.invite.backstage.service.dto.LoginRespDto;

/**
 * 操作员表相关操作
 *
 *
 * @author liuzhihang
 * @date 2019/12/17 14:57
 */
public interface OperatorService {

    /**
     * 操作员进行登陆
     * @param reqDto
     * @return
     */
    LoginRespDto login(LoginReqDto reqDto) throws BackstageException;

}
