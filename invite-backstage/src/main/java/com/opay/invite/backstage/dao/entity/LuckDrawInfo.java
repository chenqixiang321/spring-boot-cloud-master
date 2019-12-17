package com.opay.invite.backstage.dao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LuckDrawInfo implements Serializable {
    private Long id;

    private String opayId;

    private String opayName;

    private String opayPhone;

    private String prize;

    private Integer prizeLevel;

    private Integer prizePool;

    private String reference;

    private String requestid;

    private LocalDateTime createTime;

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

    public String getOpayName() {
        return opayName;
    }

    public void setOpayName(String opayName) {
        this.opayName = opayName;
    }

    public String getOpayPhone() {
        return opayPhone;
    }

    public void setOpayPhone(String opayPhone) {
        this.opayPhone = opayPhone;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public Integer getPrizeLevel() {
        return prizeLevel;
    }

    public void setPrizeLevel(Integer prizeLevel) {
        this.prizeLevel = prizeLevel;
    }

    public Integer getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(Integer prizePool) {
        this.prizePool = prizePool;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}