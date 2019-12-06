package com.opay.im.model.request;

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
@ApiModel(value = "好友信息", description = "获取opay好友信息所需参数")
public class OpayFriendRequest {

    @ApiModelProperty(value = "用户的opayId", required = true)
    private String opayId;
}
