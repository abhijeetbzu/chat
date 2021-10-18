package com.text.chat.util;

public enum ExceptionStatus {
    UNEXPECTED_ERROR(417, "Unexpected Error"),
    SQL_EXCEPTION(418, "SQL Exception"),
    ROOM_NOT_FOUND(419, "Room not found"),
    MESSAGE_NOT_FOUND(420, "Message not found"),
    USER_NOT_FOUND(421, "User not found"),
    USER_NOT_AUTHORIZED(422, "User not authorized"),
    USER_NOT_ELIGIBLE(423, "User not meeting eligibility criteria for the action requested"),
    CONVERSATION_NOT_FOUND(424, "Conversation not found"),
    USER_ALREADY_EXIST(425, "User already exist"),
    INVALID_PAYLOAD(426,"Invalid Request"),
    INVALID_CRED(427,"Invalid Credentials");

    private final int code;
    private final String reasonPhrase;

    ExceptionStatus(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public int code() {
        return code;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
