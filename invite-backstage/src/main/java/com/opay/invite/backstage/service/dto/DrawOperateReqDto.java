package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuzhihang
 * @date 2019/12/18 10:07
 */
@Data
@ApiModel(value = "操作员操作Dto")
public class DrawOperateReqDto extends BaseReqDto {

    @ApiModelProperty(value = "记录Id")
    private Long id;

    @ApiModelProperty(value = "操作备注")
    private String memo;
}
