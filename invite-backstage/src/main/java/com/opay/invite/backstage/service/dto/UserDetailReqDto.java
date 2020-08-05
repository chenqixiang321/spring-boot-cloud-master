package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuzhihang
 * @date 2019/12/18 16:58
 */
@Data
@ApiModel(value = "用户提现详情请求API", description = "点击用户id获取用户积分来源")
public class UserDetailReqDto extends BasePageReqDto {

    @ApiModelProperty(value = "用户opayId")
    private String opayId;

    @ApiModelProperty(value = "主键id")
    private Long id;

}
