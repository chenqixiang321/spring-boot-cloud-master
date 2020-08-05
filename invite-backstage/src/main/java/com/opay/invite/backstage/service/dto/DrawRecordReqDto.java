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
@ApiModel(value = "查询抽奖记录", description = "查询抽奖记录请求参数")
public class DrawRecordReqDto extends BasePageReqDto {

    @ApiModelProperty(value = "奖品")
    private String prize;

    @ApiModelProperty(value = "审核时间 开始时间")
    private String operateStartTime;

    @ApiModelProperty(value = "审核时间 结束时间")
    private String operateEndTime;

    @ApiModelProperty(value = "中奖者手机号")
    private String opayPhone;

    @ApiModelProperty(value = "赎回状态 0-未赎回 1-已赎回，留空或其他查询全部")
    private Byte redeemStatus;

    @ApiModelProperty(value = "用户opayId")
    private String opayId;

}
