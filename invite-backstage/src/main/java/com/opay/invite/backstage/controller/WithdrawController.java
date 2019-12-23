package com.opay.invite.backstage.controller;

import com.alibaba.fastjson.JSON;
import com.opay.invite.backstage.exception.BackstageException;
import com.opay.invite.backstage.exception.BackstageExceptionEnum;
import com.opay.invite.backstage.service.WithdrawService;
import com.opay.invite.backstage.service.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 阶梯拉新运营后台操作
 *
 * @author liuzhihang
 * @date 2019/12/18 14:20
 */
@Slf4j
@Api(value = "提现后台操作API")
@RestController
@RequestMapping(value = "/withdraw")
public class WithdrawController {

    @Resource
    private WithdrawService withdrawService;

    @PostMapping(value = "/withdrawRecord")
    @ApiOperation(value = "提现记录查询", notes = "体现记录查询列表")
    public WithdrawRecordRespDto withdrawRecord(@RequestBody WithdrawRecordReqDto withdrawRecordReqDto) {

        log.info("运营后台请求查询提现记录, 请求参数:{}", JSON.toJSONString(withdrawRecordReqDto));

        WithdrawRecordRespDto respDto = new WithdrawRecordRespDto();

        try {
            checkWithdrawRecordReqDto(withdrawRecordReqDto);
            respDto = withdrawService.withdrawRecord(withdrawRecordReqDto);

            respDto.buildSuccess();
        } catch (BackstageException e) {
            log.error("运营后台请求查询提现记录ERROR", e);
            respDto.buildError(e);
        } catch (Exception e) {
            log.error("运营后台请求查询提现记录ERROR", e);
            respDto.buildFail();
        }
        return respDto;
    }

    @PostMapping(value = "/userDetail")
    @ApiOperation(value = "用户详情")
    public UserDetailRespDto userDetail(@RequestBody UserDetailReqDto reqDto) {

        log.info("获取用户详情, 请求参数:{}", JSON.toJSONString(reqDto));

        UserDetailRespDto respDto = new UserDetailRespDto();

        try {
            if (reqDto == null) {
                throw new BackstageException(BackstageExceptionEnum.PARAMETER_EMPTY);
            }
            if (StringUtils.isBlank(reqDto.getOperatorId())) {
                throw new BackstageException(BackstageExceptionEnum.OPERATOR_ID_ERROR);
            }
            if (StringUtils.isBlank(reqDto.getOpayId())) {
                throw new BackstageException(BackstageExceptionEnum.OPAY_ID_EMPTY);
            }
            if (reqDto.getId() == null) {
                throw new BackstageException(BackstageExceptionEnum.WITHDRAW_ID_EMPTY);
            }

            if (reqDto.getPageNum() == null) {
                reqDto.setPageNum(1);
            }
            if (reqDto.getPageSize() == null) {
                reqDto.setPageSize(20);
            }

            respDto = withdrawService.userDetail(reqDto);
            respDto.buildSuccess();

        } catch (BackstageException e) {
            log.error("运营后台查询用户详情ERROR", e);
            respDto.buildError(e);
        } catch (Exception e) {
            log.error("运营后台查询用户详情ERROR", e);
            respDto.buildFail();
        }
        return respDto;

    }


    @ApiOperation(value = "提现审批")
    @PostMapping(value = "/withdrawOperate")
    public BaseRespDto withdrawOperate(@RequestBody WithdrawOperateReqDto reqDto) {
        BaseRespDto respDto = new BaseRespDto();

        try {
            if (StringUtils.isBlank(reqDto.getOperatorId())) {
                throw new BackstageException(BackstageExceptionEnum.OPERATOR_ID_ERROR);
            }

            if (reqDto.getStatus() == null) {
                throw new BackstageException(BackstageExceptionEnum.WITHDRAW_OPERATE_STATUS_ERROR);
            }
            if (reqDto.getStatus() != (byte) 1 && reqDto.getStatus() != (byte) 2) {
                throw new BackstageException(BackstageExceptionEnum.WITHDRAW_OPERATE_STATUS_ERROR);
            }
            if (reqDto.getId() == null) {
                throw new BackstageException(BackstageExceptionEnum.WITHDRAW_OPERATE_STATUS_ERROR);
            }


            withdrawService.withdrawOperate(reqDto);
            respDto.buildSuccess();
        } catch (BackstageException e) {
            log.error("运营后台审批提现ERROR", e);
            respDto.buildError(e);
        } catch (Exception e) {
            log.error("运营后台审批提现ERROR", e);
            respDto.buildFail();
        }

        return respDto;

    }


    private void checkWithdrawRecordReqDto(WithdrawRecordReqDto reqDto) throws BackstageException {

        if (reqDto == null) {
            throw new BackstageException(BackstageExceptionEnum.PARAMETER_EMPTY);
        }
        if (reqDto.getPageNum() == null) {
            reqDto.setPageNum(1);
        }
        if (reqDto.getPageSize() == null) {
            reqDto.setPageSize(20);
        }
        if (StringUtils.isBlank(reqDto.getOperatorId())) {
            throw new BackstageException(BackstageExceptionEnum.OPERATOR_ID_ERROR);
        }

    }


}
