package com.opay.invite.backstage.service;

import com.opay.invite.backstage.exception.BackstageException;
import com.opay.invite.backstage.service.dto.*;

/**
 * 提现表相关操作service
 *
 * @author liuzhihang
 * @date 2019/12/18 15:27
 */
public interface WithdrawService {


    /**
     * 查询提现记录
     *
     * @param reqDto
     * @return
     */
    WithdrawRecordRespDto withdrawRecord(WithdrawRecordReqDto reqDto) throws Exception;

    /**
     * 查询用户详情
     *
     * @param reqDto
     * @return
     */
    UserDetailRespDto userDetail(UserDetailReqDto reqDto) throws Exception;

    /**
     * 提现审核
     *
     * @param reqDto
     * @throws BackstageException
     */
    void withdrawOperate(WithdrawOperateReqDto reqDto) throws Exception;
}
