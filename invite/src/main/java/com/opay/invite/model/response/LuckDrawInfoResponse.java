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
    @ApiModelProperty(value = "用户剩余抽奖次数")
    private int userCount;

}
