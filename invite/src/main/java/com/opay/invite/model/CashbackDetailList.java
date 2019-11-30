package com.opay.invite.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "关系列表", description = "关系列表")
public class CashbackDetailList {

    @ApiModelProperty(value = "总收益")
    private BigDecimal totalReward;

    @ApiModelProperty(value = "钱包额")
    private BigDecimal cashReward;

    @ApiModelProperty(value = "list")
    private List<OpayMasterPupilAwardVo> list;
}
