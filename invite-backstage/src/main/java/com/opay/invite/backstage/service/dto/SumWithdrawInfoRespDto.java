package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuzhihang
 * @date 2019/12/24 15:52
 */
@Data
public class SumWithdrawInfoRespDto extends BaseRespDto {

    @ApiModelProperty(value = "toBonus的记录数")
    private Integer toBonusRecordSum;
    @ApiModelProperty(value = "toBonus总数")
    private String toBonusSum;

    @ApiModelProperty(value = "toBalance的记录数")
    private Integer toBalanceRecordSum;

    @ApiModelProperty(value = "toBalance数量")
    private String toBalanceSum;

    @ApiModelProperty(value = "总记录数")
    private Integer totalRecordSum;
    @ApiModelProperty(value = "总数量")
    private String totalSum;

    @ApiModelProperty(value = "拒绝记录总数")
    private Integer rejectRecordSum;

    @ApiModelProperty(value = "拒绝数量")
    private String rejectSum;

}
