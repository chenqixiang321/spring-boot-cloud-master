package com.opay.invite.backstage.service.dto;

import com.opay.invite.backstage.constant.DateTimeConstant;
import com.opay.invite.backstage.dao.entity.LuckDrawInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 抽奖记录 （中奖记录）
 * 当前版本仅支持查询中奖的记录
 *
 * @author liuzhihang
 * @date 2019/12/17 18:48
 */
@Data
@ApiModel(value = "抽奖记录DTO", description = "抽奖记录列表内容")
public class DrawRecordDto implements Serializable {

    @ApiModelProperty(value = "本条中奖记录数据库主键ID")
    private Long id;

    @ApiModelProperty(value = "中奖者opayId")
    private String opayId;

    @ApiModelProperty(value = "中奖者opayName")
    private String opayName;

    @ApiModelProperty(value = "中奖者手机号")
    private String opayPhone;

    @ApiModelProperty(value = "奖品")
    private String prize;

    @ApiModelProperty(value = "奖品级别")
    private Integer prizeLevel;

    @ApiModelProperty(value = "奖池信息")
    private Integer prizePool;

    @ApiModelProperty(value = "操作员Id")
    private String operatorId;
    @ApiModelProperty(value = "操作员名称")
    private String operatorName;
    @ApiModelProperty(value = "备注")
    private String memo;
    @ApiModelProperty(value = "创建时间（即中奖时间）")
    private String createTime;
    @ApiModelProperty(value = "操作员操作时间")
    private String operatorTime;

    public DrawRecordDto convertFrom(LuckDrawInfo luckDrawInfo) {

        BeanUtils.copyProperties(luckDrawInfo, this);
        this.createTime = luckDrawInfo.getCreateTime().format(DateTimeConstant.FORMAT_TIME);
        this.operatorTime = luckDrawInfo.getOperateTime() == null ? null : luckDrawInfo.getOperateTime().format(DateTimeConstant.FORMAT_TIME);
        return this;
    }

}
