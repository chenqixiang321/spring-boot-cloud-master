package com.opay.invite.backstage.service;

import com.opay.invite.backstage.dao.entity.OpayActiveTixian;
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

    /**
     * 获取提现审批总量
     *
     * @param reqDto
     * @return
     */
    SumWithdrawInfoRespDto sumWithdrawInfo(SumWithdrawInfoReqDto reqDto);

    /**
     * 提现失败回滚操作
     * @param saveTixian
     */
    void rollbackTixian(OpayActiveTixian saveTixian);

    /**
     * 处理提现转账异步通知(也称奖励金)
     */
    void transferNotify(OpayActiveTixian opayActiveTixianOrg, String orderNo, String orderStatus);
}
