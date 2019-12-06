package com.opay.invite.model;

import com.opay.invite.stateconfig.AgentRoyaltyReward;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "活动页", description = "活动页")
public class OpayActivity {
    @ApiModelProperty(value = "是否存在好友0：不存在，1：存在")
    private int isFriend;

    @ApiModelProperty(value = "用户钱包总额")
    private BigDecimal amount=BigDecimal.ZERO;

    @ApiModelProperty(value = "阶段数据")
    private List<StepReward> step;

    @ApiModelProperty(value = "任务完成数据,非代理按照这个展现")
    private List<OpayMasterPupilAwardVo> task;

    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

    @ApiModelProperty(value = "是否代理,0:普通，1：代理")
    private int isAgent;

    @ApiModelProperty(value = "任务完成数据,1代理徒弟展现和代理")
    private BigDecimal agentTaskReward=BigDecimal.ZERO;

    @ApiModelProperty(value = "没有邀请朋友的文案和邀请")
    private String friendText;

    @ApiModelProperty(value = "活动是否开始：0未开始，1:开始")
    private Integer isStart;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
