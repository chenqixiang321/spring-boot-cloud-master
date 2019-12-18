package com.opay.im.model;

import io.rong.messages.BaseMessage;

public class RedEnvelopeMessage extends BaseMessage {
    private String content;

    public RedEnvelopeMessage(String content) {
        this.content = content;
    }

    @Override
    public String getType() {
        return "app:red-envelope-receipt";
    }

    @Override
    public String toString() {
        return content;
    }
}
