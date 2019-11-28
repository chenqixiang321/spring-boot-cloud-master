package com.opay.invite.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ApiModel(value = "com-opay-invite-model-LuckDrawInfoResponse")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LuckDrawInfoResponse {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "中奖者的opay_id")
    private String opayId;
    @ApiModelProperty(value = "中奖者的opay名字")
    private String opayName;
    @ApiModelProperty(value = "奖品")
    private String prize;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
