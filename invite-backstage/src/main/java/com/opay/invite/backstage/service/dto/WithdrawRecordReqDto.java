package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 抽奖记录查询
 *
 * @author liuzhihang
 * @date 2019/12/17 18:24
 */
@Data
@ApiModel(value = "提现列表Dto", description = "查询拉新列表")
public class WithdrawRecordReqDto extends BasePageReqDto {

    @ApiModelProperty(value = "审核时间 开始时间")
    private String operateStartTime;

    @ApiModelProperty(value = "审核时间 结束时间")
    private String operateEndTime;

    @ApiModelProperty(value = "中奖者手机号")
    private String opayPhone;
    @ApiModelProperty(value = "0:申请中， 1:审批通过 2:审批不通过 3:转账成功 4:转账失败")
    private Byte status;

    @ApiModelProperty(value = "用户opayId")
    private String opayId;


}
