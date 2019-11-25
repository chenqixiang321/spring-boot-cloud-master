package com.opay.im.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "群免打扰", description = "群免打扰所需参数")
public class MuteGroupRequest {
    /**
     * 群id
     */
    @ApiModelProperty(value="群id")
    private Long groupId;

    @ApiModelProperty(value="群成员的opay_id",hidden = true)
    private String opayId;

    @ApiModelProperty(value="是否开启免打扰 true:开启 false;关闭")
    private boolean IsMute;
}
