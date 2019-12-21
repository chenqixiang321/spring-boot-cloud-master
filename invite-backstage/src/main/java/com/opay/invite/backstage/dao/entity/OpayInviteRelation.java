package com.opay.invite.backstage.dao.entity;

import java.time.LocalDateTime;

public class OpayInviteRelation {
    private Long id;

    private String masterId;

    private String pupilId;

    private String pupilPhone;

    private String masterPhone;

    private LocalDateTime createAt;

    private String masterParentId;

    private Byte markType;

    private Integer month;

    private Integer day;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getPupilId() {
        return pupilId;
    }

    public void setPupilId(String pupilId) {
        this.pupilId = pupilId;
    }

    public String getPupilPhone() {
        return pupilPhone;
    }

    public void setPupilPhone(String pupilPhone) {
        this.pupilPhone = pupilPhone;
    }

    public String getMasterPhone() {
        return masterPhone;
    }

    public void setMasterPhone(String masterPhone) {
        this.masterPhone = masterPhone;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getMasterParentId() {
        return masterParentId;
    }

    public void setMasterParentId(String masterParentId) {
        this.masterParentId = masterParentId;
    }

    public Byte getMarkType() {
        return markType;
    }

    public void setMarkType(Byte markType) {
        this.markType = markType;
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
}