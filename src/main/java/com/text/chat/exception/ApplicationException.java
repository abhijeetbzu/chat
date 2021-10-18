package com.text.chat.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.text.chat.util.ExceptionStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIncludeProperties({"message", "code"})
public class ApplicationException extends Exception {
    private int code;

    public ApplicationException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ApplicationException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus.getReasonPhrase());
        this.code = exceptionStatus.code();
    }

    public ApplicationException(String message, ExceptionStatus exceptionStatus) {
        super(message);
        this.code = exceptionStatus.code();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }
}