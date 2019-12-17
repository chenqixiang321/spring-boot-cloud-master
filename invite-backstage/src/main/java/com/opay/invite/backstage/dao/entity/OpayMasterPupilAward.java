package com.opay.invite.backstage.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpayMasterPupilAward implements Serializable {
    private Long id;

    private String opayId;

    private String pupilId;

    private BigDecimal reward;

    private Date createAt;

    private Boolean status;

    private Boolean action;

    private BigDecimal amount;

    private Boolean markType;

    private String stepJson;

    private BigDecimal masterReward;

    private Boolean masterType;

    private Integer month;

    private Integer day;

    private String orderId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpayId() {
        return opayId;
    }

    public void setOpayId(String opayId) {
        this.opayId = opayId;
    }

    public String getPupilId() {
        return pupilId;
    }

    public void setPupilId(String pupilId) {
        this.pupilId = pupilId;
    }

    public BigDecimal getReward() {
        return reward;
    }

    public void setReward(BigDecimal reward) {
        this.reward = reward;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getAction() {
        return action;
    }

    public void setAction(Boolean action) {
        this.action = action;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getMarkType() {
        return markType;
    }

    public void setMarkType(Boolean markType) {
        this.markType = markType;
    }

    public String getStepJson() {
        return stepJson;
    }

    public void setStepJson(String stepJson) {
        this.stepJson = stepJson;
    }

    public BigDecimal getMasterReward() {
        return masterReward;
    }

    public void setMasterReward(BigDecimal masterReward) {
        this.masterReward = masterReward;
    }

    public Boolean getMasterType() {
        return masterType;
    }

    public void setMasterType(Boolean masterType) {
        this.masterType = masterType;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}