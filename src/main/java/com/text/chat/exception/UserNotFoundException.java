package com.text.chat.exception;

import com.text.chat.util.ExceptionStatus;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException() {
        super(ExceptionStatus.USER_NOT_FOUND);
    }

    public UserNotFoundException(String message, int code) {
        super(message, code);
    }

    public UserNotFoundException(String message) {
        super(message, ExceptionStatus.USER_NOT_FOUND);
    }
}
