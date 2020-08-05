package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuzhihang
 * @date 2019/12/24 15:52
 */
@Data
public class SumWithdrawInfoReqDto extends BaseReqDto {

    @ApiModelProperty("查询请求时间, 默认 当天时间 00:00:00")
    private String startTime;

    @ApiModelProperty("查询结束时间, 默认 当天 23:59:59")
    private String endTime;

}
