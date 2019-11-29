package com.opay.invite.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "邀请奖励结果", description = "邀请奖励结果")
public class OpayMasterPupilAwardVo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "奖励金额")
    private BigDecimal reward;

    @ApiModelProperty(value = "收益时间")
    private Date createAt;

    @ApiModelProperty(value = "收益状态")
    private int status;

    @ApiModelProperty(value = "行为:0:注册、充值")
    private int action;

    @ApiModelProperty(value = "行为名称")
    private String actionName;

}
