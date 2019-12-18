package com.opay.im.model;

import io.rong.messages.BaseMessage;
import io.rong.models.message.MessageModel;

public class Message extends MessageModel {
    public String count;
    public Integer isPersisted;
    public Integer isCounted;
    public Integer verifyBlacklist;
    public Integer isIncludeSender;
    public Integer contentAvailable;

    public Message() {
    }

    public Message(String senderId, String[] targetId, String objectName, BaseMessage content, String pushContent, String pushData, String count, Integer isPersisted, Integer isCounted, Integer verifyBlacklist, Integer isIncludeSender, Integer contentAvailable) {
        super(senderId, targetId, objectName, content, pushContent, pushData);
        this.count = count;
        this.isPersisted = isPersisted;
        this.isCounted = isCounted;
        this.verifyBlacklist = verifyBlacklist;
        this.isIncludeSender = isIncludeSender;
        this.contentAvailable = contentAvailable;
    }

    public Message setSenderId(String senderId) {
        super.setSenderId(senderId);
        return this;
    }

    public String[] getTargetId() {
        return super.getTargetId();
    }

    public Message setTargetId(String[] targetId) {
        super.setTargetId(targetId);
        return this;
    }

    public Message setContent(BaseMessage content) {
        super.setContent(content);
        return this;
    }

    public Message setPushContent(String pushContent) {
        super.setPushContent(pushContent);
        return this;
    }

    public Message setPushData(String pushData) {
        super.setPushData(pushData);
        return this;
    }

    public String getCount() {
        return this.count;
    }

    public Message setCount(String count) {
        this.count = count;
        return this;
    }

    public Integer getVerifyBlacklist() {
        return this.verifyBlacklist;
    }

    public Message setVerifyBlacklist(Integer verifyBlacklist) {
        this.verifyBlacklist = verifyBlacklist;
        return this;
    }

    public Integer getIsPersisted() {
        return this.isPersisted;
    }

    public Message setIsPersisted(Integer isPersisted) {
        this.isPersisted = isPersisted;
        return this;
    }

    public Integer getIsCounted() {
        return this.isCounted;
    }

    public Message setIsCounted(Integer isCounted) {
        this.isCounted = isCounted;
        return this;
    }

    public Integer getIsIncludeSender() {
        return this.isIncludeSender;
    }

    public Message setIsIncludeSender(Integer isIncludeSender) {
        this.isIncludeSender = isIncludeSender;
        return this;
    }

    public Message setObjectName(String objectName) {
        super.setObjectName(objectName);
        return this;
    }

    public Integer getContentAvailable() {
        return this.contentAvailable;
    }

    public Message setContentAvailable(Integer contentAvailable) {
        this.contentAvailable = contentAvailable;
        return this;
    }
}
