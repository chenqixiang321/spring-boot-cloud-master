package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuzhihang
 * @date 2019/12/18 21:17
 */
@Data
@ApiModel(value = "提现审核")
public class WithdrawOperateReqDto extends BaseReqDto {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "1 审核通过 2 审核不通过")
    private Integer status;

    @ApiModelProperty(value = "审核备注")
    private String memo;

}
