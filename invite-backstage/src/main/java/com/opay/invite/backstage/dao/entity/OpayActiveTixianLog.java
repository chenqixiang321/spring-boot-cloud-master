package com.opay.invite.backstage.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpayActiveTixianLog implements Serializable {
    private Long id;

    private String opayId;

    private BigDecimal amount;

    private Boolean type;

    private Date createAt;

    private Long tixianId;

    private Integer month;

    private Integer day;

    private String deviceId;

    private String ip;

    private Boolean mark;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getTixianId() {
        return tixianId;
    }

    public void setTixianId(Long tixianId) {
        this.tixianId = tixianId;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getMark() {
        return mark;
    }

    public void setMark(Boolean mark) {
        this.mark = mark;
    }
}