package com.opay.invite.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ApiModel(value = "com-opay-invite-model-LuckDrawInfoResponse")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LuckDrawInfoResponse {

    @ApiModelProperty(value = "奖品")
    private String prize;
    @ApiModelProperty(value = "活动剩余抽奖次数")
    private int activityCount;
    @ApiModelProperty(value = "用户剩余抽奖次数-登陆")
    private Integer loginCount;
    @ApiModelProperty(value = "用户剩余抽奖次数-分享")
    private Integer shareCount;
    @ApiModelProperty(value = "用户剩余抽奖次数-邀请")
    private Integer inviteCount;

}
