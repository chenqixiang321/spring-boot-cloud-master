package com.opay.invite.model;

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
@ApiModel(value = "抽奖信息", description = "抽奖信息")
public class LuckDrawModel {

    @ApiModelProperty(value = "剩余抽奖次数")
    private int count;
}
