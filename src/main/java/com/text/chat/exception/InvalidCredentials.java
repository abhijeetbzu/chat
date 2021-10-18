package com.text.chat.exception;

import com.text.chat.util.ExceptionStatus;

public class InvalidCredentials extends ApplicationException {
    public InvalidCredentials(String message, int code) {
        super(message, code);
    }

    public InvalidCredentials() {
        super(ExceptionStatus.INVALID_CRED);
    }

    public InvalidCredentials(String message) {
        super(message);
    }
}
