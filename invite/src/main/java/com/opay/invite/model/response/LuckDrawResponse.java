package com.opay.invite.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "抽奖信息", description = "抽奖信息")
public class LuckDrawResponse {

    @ApiModelProperty(value = "剩余抽奖次数")
    private int count;
    @ApiModelProperty(value = "下场开始时间")
    private Date startTime;
}
