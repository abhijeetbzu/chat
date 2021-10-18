package com.text.chat.util;

import com.text.chat.model.Message;
import com.text.chat.model.Room;
import com.text.chat.model.User;
import com.text.chat.response.MessageControllerResp;
import com.text.chat.response.RoomControllerResp;
import com.text.chat.response.UserControllerResp;

import java.util.List;

public class ResponseUtil {
    public static MessageControllerResp formMsgResp(String message, int statusCode) {
        MessageControllerResp resp = new MessageControllerResp();

        resp.setMsg(message);
        resp.setCode(statusCode);

        return resp;
    }

    public static MessageControllerResp formMsgResp(ApplicationStatus applicationStatus) {
        return formMsgResp(applicationStatus.message(), applicationStatus.code());
    }

    public static MessageControllerResp addMessagesToResp(List<Message> messages, ApplicationStatus applicationStatus) {
        MessageControllerResp resp = formMsgResp(applicationStatus);
        resp.setMessages(messages);
        return resp;
    }

    public static UserControllerResp formUserResp(String message, int statusCode) {
        UserControllerResp resp = new UserControllerResp();

        resp.setMsg(message);
        resp.setCode(statusCode);

        return resp;
    }

    public static UserControllerResp formUserResp(ApplicationStatus applicationStatus) {
        return formUserResp(applicationStatus.message(), applicationStatus.code());
    }

    public static UserControllerResp addUserToUserResp(User user, ApplicationStatus applicationStatus) {
        UserControllerResp resp = formUserResp(applicationStatus);
        resp.setUser(user);
        return resp;
    }

    public static RoomControllerResp formRoomResp(String message, int statusCode) {
        RoomControllerResp resp = new RoomControllerResp();

        resp.setMsg(message);
        resp.setCode(statusCode);
        return resp;
    }

    public static RoomControllerResp addRoomToRoomResp(ApplicationStatus applicationStatus, Room room) {
        RoomControllerResp resp = formRoomResp(applicationStatus.message(), applicationStatus.code());
        resp.setRoom(room);
        return resp;
    }

    public static RoomControllerResp formRoomResp(ApplicationStatus applicationStatus) {
        return formRoomResp(applicationStatus.message(), applicationStatus.code());
    }
}
