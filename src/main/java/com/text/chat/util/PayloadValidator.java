package com.text.chat.util;

import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Message;
import com.text.chat.model.Room;
import com.text.chat.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

public class PayloadValidator {
    private static final Logger LOGGER = LogManager.getLogger(PayloadValidator.class);
    private static final String MISSING_REQUESTEDBY = "requestedBy not specified";
    private static final String MISSING_REQUESTEDBY_ID = "requestedBy ID not specified";
    private static final String MISSING_ROOM = "room details missing";
    private static final String MISSING_USER = "user details missing";
    private static final String MISSING_MESSAGE = "message details missing";
    private static final String MISSING_ROOM_ID = "room id missing";
    private static final String MISSING_MESSAGE_ID = "message id missing";
    private static final String MISSING_USER_ID = "USER id missing";

    public static void validateMessage(Message message) throws ApplicationException {
        validateMessage(message, true);
    }

    public static void validateMessage(Message message, boolean checkForId) throws ApplicationException {
        validateMessage(message, checkForId, true);
    }

    public static void validateMessage(Message message, boolean checkForId,
                                       boolean checkForRequestedBy) throws ApplicationException {
        if (message == null) {
            LOGGER.info(MISSING_MESSAGE);
            throw new ApplicationException(MISSING_MESSAGE, ExceptionStatus.INVALID_PAYLOAD);
        }

        if (checkForId) {
            if (message.getId() == null) {
                LOGGER.info(MISSING_MESSAGE_ID);
                throw new ApplicationException(MISSING_MESSAGE_ID, ExceptionStatus.INVALID_PAYLOAD);
            }
        }

        if (checkForRequestedBy)
            checkRequestedBy(message.getRequestedBy());
    }

    public static void validateRoom(Room room) throws ApplicationException {
        validateRoom(room, true);
    }

    public static void validateRoom(Room room, boolean checkForId) throws ApplicationException {
        validateRoom(room, checkForId, true);
    }

    public static void validateRoom(Room room, boolean checkForId, boolean checkForRequestedBy) throws ApplicationException {
        if (room == null) {
            LOGGER.info(MISSING_ROOM);
            throw new ApplicationException(MISSING_ROOM, ExceptionStatus.INVALID_PAYLOAD);
        }

        if (checkForId) {
            if (room.getId() == null) {
                LOGGER.info(MISSING_ROOM_ID);
                throw new ApplicationException(MISSING_ROOM_ID, ExceptionStatus.INVALID_PAYLOAD);
            }
        }

        if (checkForRequestedBy)
            checkRequestedBy(room.getRequestedBy());
    }

    public static void validateUser(User user) throws ApplicationException {
        validateUser(user, true);
    }

    public static void validateUser(User user, boolean checkForId) throws ApplicationException {
        validateUser(user, checkForId, true);
    }

    public static void validateUser(User user, boolean checkForId, boolean checkForRequestedBy) throws ApplicationException {
        if (user == null) {
            LOGGER.info(MISSING_USER);
            throw new ApplicationException(MISSING_USER, ExceptionStatus.INVALID_PAYLOAD);
        }

        if (checkForId) {
            if (user.getId() == null) {
                LOGGER.info(MISSING_USER_ID);
                throw new ApplicationException(MISSING_USER_ID, ExceptionStatus.INVALID_PAYLOAD);
            }
        }

        if (checkForRequestedBy)
            checkRequestedBy(user.getRequestedBy());
    }

    public static void checkRequestedBy(User requestedBy) throws ApplicationException {
        if (requestedBy == null) {
            LOGGER.info(MISSING_REQUESTEDBY);
            throw new ApplicationException(MISSING_REQUESTEDBY, ExceptionStatus.INVALID_PAYLOAD);
        }

        if (!StringUtils.hasText(requestedBy.getId())) {
            LOGGER.info(MISSING_REQUESTEDBY_ID);
            throw new ApplicationException(MISSING_REQUESTEDBY_ID, ExceptionStatus.INVALID_PAYLOAD);
        }
    }
}
