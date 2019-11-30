package com.opay.im.model;

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
public class RongCloudMessageModel {
    private String msgUID;

    private String fromUserId;

    private String toUserId;

    private String objectName;

    private String content;

    private String channelType;

    private String msgTimestamp;

    private int sensitiveType;

    private String source;

    private String groupUserIds;
}