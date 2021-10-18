package com.text.chat.util;

public enum ApplicationStatus {
    CREATED_SUCCESSFULLY(201, "Created successfully"),
    UPDATED_SUCCESSFULLY(202, "Updated successfully"),
    FETCHED_SUCCESSFULLY(203, "Fetched successfully"),
    DELETED_SUCCESSFULLY(204, "Deleted successfully"),
    SENT_SUCCESSFULLY(205, "Sent successfully");
    private final int code;
    private final String message;

    ApplicationStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
