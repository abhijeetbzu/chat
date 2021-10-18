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
import java.sql.SQLException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorController {
    private static final Logger LOGGER = LogManager.getLogger(ErrorController.class);

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApplicationException handleSqlException(SQLException ex, HttpServletRequest request) {
        LOGGER.error("SQL Exception: ", ex);
        return new ApplicationException(ExceptionStatus.UNEXPECTED_ERROR.getReasonPhrase(),
                ExceptionStatus.SQL_EXCEPTION.code());
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApplicationException handleApplicationException(ApplicationException ex, HttpServletRequest request) {
        LOGGER.error("Application Exception: ", ex);
        return ex;
    }
}
