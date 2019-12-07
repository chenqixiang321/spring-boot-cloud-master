package com.opay.invite.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "邀请奖励结果", description = "邀请奖励结果")
public class OpayMasterPupilAwardVo {

    @ApiModelProperty(value = "主键",hidden = true)
    private Long id;

    @ApiModelProperty(value = "奖励金额")
    private BigDecimal reward=BigDecimal.ZERO;

    @ApiModelProperty(value = "收益时间",hidden = true)
    @JsonFormat()
    private Date createAt;

    @ApiModelProperty(value = "收益状态",hidden = true)
    private int status;

    @ApiModelProperty(value = "行为:1:注册、2:充值")
    private int action;

    @ApiModelProperty(value = "行为名称",hidden = true)
    private String actionName;

    @ApiModelProperty(value = "收益时间，long类型，到毫秒")
    private Long createTime;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "0:徒弟 1:师傅")
    private Integer masterType;

    @ApiModelProperty(value = "徒弟电话号",hidden = true)
    @JsonIgnore
    private String pupilPhone;
}
