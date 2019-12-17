package com.opay.invite.backstage.controller;

import com.alibaba.fastjson.JSON;
import com.opay.invite.backstage.exception.BackstageException;
import com.opay.invite.backstage.exception.BackstageExceptionEnum;
import com.opay.invite.backstage.service.OperatorService;
import com.opay.invite.backstage.service.dto.LoginReqDto;
import com.opay.invite.backstage.service.dto.LoginRespDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 操作员相关controller
 *
 * @author liuzhihang
 * @date 2019/12/17 14:35
 */
@Slf4j
@RestController
@RequestMapping(value = "operator")
public class OperatorController {

    @Resource
    private OperatorService operatorService;


    @PostMapping(value = "/login")
    public LoginRespDto login(@RequestBody LoginReqDto loginReqDto) {

        log.info("操作员请求登陆， 请求参数：{}", JSON.toJSONString(loginReqDto));

        LoginRespDto respDto = new LoginRespDto();

        try {
            checkParam(loginReqDto);
            operatorService.login(loginReqDto);
            respDto.buildSuccess();
        } catch (BackstageException e) {
            log.error("操作员登陆失败", e);
            respDto.buildError(e);
        } catch (Exception e) {
            log.error("操作员登陆失败", e);
            respDto.buildFail();
        }
        return respDto;

    }

    private void checkParam(LoginReqDto loginReqDto) throws BackstageException {
        if (loginReqDto == null) {
            throw new BackstageException(BackstageExceptionEnum.PARAMETER_EMPTY);
        }
        if (StringUtils.isBlank(loginReqDto.getOperatorId())) {
            throw new BackstageException(BackstageExceptionEnum.OPERATOR_ID_ERROR);
        }
        if (StringUtils.isBlank(loginReqDto.getLoginPwd())) {
            throw new BackstageException(BackstageExceptionEnum.OPERATOR_PWD_ERROR);
        }


    }


}
