package com.opay.invite.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PrizePoolResponse {
    private Integer prize;
    private String message;
    private Integer pool;
    private Integer activityCount;
    private Integer loginCount;
    private Integer shareCount;
    private Integer inviteCount;
}
