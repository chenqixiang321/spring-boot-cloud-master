package com.opay.invite.backstage.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class OpayInviteRelation implements Serializable {
    private Long id;

    private String masterId;

    private String pupilId;

    private String pupilPhone;

    private String masterPhone;

    private Date createAt;

    private String masterParentId;

    private Boolean markType;

    private Integer month;

    private Integer day;

    private static final long serialVersionUID = 1L;

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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getMasterParentId() {
        return masterParentId;
    }

    public void setMasterParentId(String masterParentId) {
        this.masterParentId = masterParentId;
    }

    public Boolean getMarkType() {
        return markType;
    }

    public void setMarkType(Boolean markType) {
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