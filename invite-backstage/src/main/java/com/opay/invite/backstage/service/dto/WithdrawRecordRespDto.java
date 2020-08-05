package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 抽奖记录查询
 *
 * @author liuzhihang
 * @date 2019/12/17 18:24
 */
@Data
@ApiModel(value = "提现列表返回", description = "提现列表返回参数")
public class WithdrawRecordRespDto extends BasePageRespDto {

    @ApiModelProperty(value = "提现列表对象")
    private List<WithdrawRecordDto> withdrawRecordDtoList;

}
