package com.opay.im.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GrabLuckyMoneyResult {
    private String code;
    private Long id;
    private BigDecimal amount;
    private String message;
}
