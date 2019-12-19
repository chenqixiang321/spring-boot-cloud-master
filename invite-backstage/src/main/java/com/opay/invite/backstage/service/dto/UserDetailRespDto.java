package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuzhihang
 * @date 2019/12/18 16:58
 */
@Data
@ApiModel(value = "用户提现详情")
public class UserDetailRespDto extends BasePageRespDto {

    @ApiModelProperty(value = "该笔提现对应的内部流水号")
    private String reference;

    @ApiModelProperty(value = "用户opayId")
    private String opayId;

    @ApiModelProperty(value = "用户总邀请人数")
    private Integer inviteNo;
    @ApiModelProperty(value = "用户总获取到的cashback")
    private String totalCashback;
    @ApiModelProperty(value = "钱包剩余event cashback数")
    private String remainCashback;
    @ApiModelProperty(value = "成功提现到bonus的数量")
    private String toBonus;
    @ApiModelProperty(value = "成功提现到balance的数量")
    private String toBalance;
    @ApiModelProperty(value = "注册时间")
    private String registerTime;
    @ApiModelProperty(value = "本次提现的金额")
    private String withdrawAmount;
    @ApiModelProperty(value = "本次提现的方式")
    private Byte withdrawAmountType;
    @ApiModelProperty(value = "0:申请中， 1:审批通过 2:审批不通过 3:转账成功 4:转账失败")
    private Byte status;
    @ApiModelProperty(value = "备注")
    private String memo;
    @ApiModelProperty(value = "用户获取的奖励Dto集合")
    private List<UserRewardDto> userRewardDtoList;

}
