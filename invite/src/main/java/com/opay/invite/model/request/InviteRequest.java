package com.opay.invite.model.request;

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
@ApiModel(value = "InviteRequest邀请关系", description = "InviteRequest邀请关系")
public class InviteRequest {

    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

    @ApiModelProperty(value = "当前页码")
    private int pageNum;

    @ApiModelProperty(value = "当前页大小")
    private int pageSize;

}
