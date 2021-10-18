package com.text.chat.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.text.chat.model.Message;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageControllerResp extends ApplicationResp{
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
