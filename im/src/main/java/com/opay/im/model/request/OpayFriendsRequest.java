package com.opay.im.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "通讯录好友", description = "获取通讯录中是否有opay所需参数")
public class OpayFriendsRequest {

    @ApiModelProperty(value = "用户手机号数 格式必须+234开头", required = true)
    private List<String> mobiles;
    @ApiModelProperty(value = "开始时间 格式 yyyy-MM-dd HH:mm:ss")
    private String startTime;
    @ApiModelProperty(value = "是否拉取有过交易记录的opay账户")
    private boolean isLoadTradePhone;
}
