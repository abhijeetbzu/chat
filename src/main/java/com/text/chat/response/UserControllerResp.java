package com.text.chat.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.text.chat.model.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserControllerResp extends ApplicationResp {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
