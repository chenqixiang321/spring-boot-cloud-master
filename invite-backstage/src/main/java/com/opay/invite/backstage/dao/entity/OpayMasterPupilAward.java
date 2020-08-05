package com.opay.invite.backstage.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OpayMasterPupilAward implements Serializable {
    private Long id;

    private String opayId;

    private String pupilId;

    private BigDecimal reward;

    private LocalDateTime createAt;

    private Byte status;

    private Byte action;

    private BigDecimal amount;

    private Byte markType;

    private String stepJson;

    private BigDecimal masterReward;

    private Byte masterType;

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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getAction() {
        return action;
    }

    public void setAction(Byte action) {
        this.action = action;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Byte getMarkType() {
        return markType;
    }

    public void setMarkType(Byte markType) {
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

    public Byte getMasterType() {
        return masterType;
    }

    public void setMasterType(Byte masterType) {
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