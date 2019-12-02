package com.opay.invite.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "需要完成任务", description = "需要完成任务")
public class FinishTask {

    @ApiModelProperty(value = "任务类型：1：注册，2：充值")
    private int type;

    @ApiModelProperty(value = "任务奖励")
    private BigDecimal reward;


}
