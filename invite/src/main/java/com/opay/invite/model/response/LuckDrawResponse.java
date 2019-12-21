package com.opay.invite.model.response;

import com.opay.invite.model.PrizeModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "抽奖信息", description = "抽奖信息")
public class LuckDrawResponse {
    @ApiModelProperty(value = "当前场开始时间")
    private String currentStartTime;
    @ApiModelProperty(value = "当前场结束时间")
    private String currentEndTime;
    @ApiModelProperty(value = "下场开始时间")
    private String nextStartTime;
    @ApiModelProperty(value = "下场结束时间")
    private String nextEndTime;
    @ApiModelProperty(value = "是否可以抽奖")
    private boolean isCanLuckyDraw;
    @ApiModelProperty(value = "系统时间")
    private String systemTime;
    @ApiModelProperty(value = "活动是否开始：0未开始，1:开始")
    private Integer isStart;
    @ApiModelProperty(value = "奖品信息")
    private Map<Integer, PrizeModel> prizeInfo;
}
