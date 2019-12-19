package com.opay.invite.backstage.service;

import com.opay.invite.backstage.exception.BackstageException;
import com.opay.invite.backstage.service.dto.DrawOperateReqDto;
import com.opay.invite.backstage.service.dto.DrawRecordReqDto;
import com.opay.invite.backstage.service.dto.DrawRecordRespDto;

/**
 * 抽奖记录相关接口
 *
 * @author liuzhihang
 * @date 2019/12/17 19:03
 */
public interface LuckDrawInfoService {

    /**
     * 查询抽奖记录
     *
     * @param reqDto
     * @return
     */
    DrawRecordRespDto drawRecord(DrawRecordReqDto reqDto);

    /**
     * 操作中奖
     *
     * @param drawOperateReqDto
     */
    void drawOperate(DrawOperateReqDto drawOperateReqDto) throws BackstageException;
}
