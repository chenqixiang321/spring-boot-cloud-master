package com.opay.invite.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "抽奖次数", description = "抽奖次数")
public class LuckDrawCountResponse {

    @ApiModelProperty(value = "活动剩余抽奖次数")
    private int activityCount;
    @ApiModelProperty(value = "用户剩余抽奖次数-登陆")
    private Integer loginCount;
    @ApiModelProperty(value = "用户剩余抽奖次数-分享")
    private Integer shareCount;
    @ApiModelProperty(value = "当天最高分享次数")
    private Integer shareMaxCount;
    @ApiModelProperty(value = "用户剩余抽奖次数-邀请")
    private Integer inviteCount;
}
