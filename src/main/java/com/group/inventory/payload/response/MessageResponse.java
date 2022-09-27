package com.group.inventory.payload.response;

public class MessageResponse {
    private String messages;

    public MessageResponse(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
