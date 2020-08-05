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
public class MyLuckDrawListRequest {
    @ApiModelProperty(value = "当前页码,需要分页必传入")
    private int pageNum;

    @ApiModelProperty(value = "当前页大小,需要分页必传入")
    private int pageSize;
}
