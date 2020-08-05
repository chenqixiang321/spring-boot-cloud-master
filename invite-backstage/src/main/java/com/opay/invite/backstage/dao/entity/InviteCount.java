package com.opay.invite.backstage.dao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class InviteCount implements Serializable {
    private Long id;

    private String opayId;

    private String day;

    private String opayName;

    private String opayPhone;

    private Integer login;

    private Integer share;

    private Integer invite;

    private LocalDateTime createTime;

    private Long version;

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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Integer getInvite() {
        return invite;
    }

    public void setInvite(Integer invite) {
        this.invite = invite;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}