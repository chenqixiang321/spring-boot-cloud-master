package com.opay.invite.backstage.dao.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OpayActiveTixian implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String opayId;

    private BigDecimal amount;

    private Byte type;

    private LocalDateTime createAt;

    private Byte status;

    private Integer month;

    private Integer day;

    private String deviceId;

    private String ip;

    private String tradeNo;

    private String reference;

    private LocalDateTime updateAt;

    private String operator;

    private LocalDateTime operateTime;

    private String memo;
}