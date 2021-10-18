package com.text.chat.controller;

import com.text.chat.exception.ApplicationException;
import com.text.chat.util.ExceptionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultErrorController {

    private static final Logger LOGGER = LogManager.getLogger(DefaultErrorController.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApplicationException handleException(Exception ex, HttpServletRequest request) {
        LOGGER.error("Unexpected error occurred: ", ex);
        return new ApplicationException(ExceptionStatus.UNEXPECTED_ERROR);
    }
}
