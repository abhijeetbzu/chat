package com.text.chat.controller;

import com.text.chat.exception.ApplicationException;
import com.text.chat.model.User;
import com.text.chat.service.IUserService;
import com.text.chat.util.ApplicationStatus;
import com.text.chat.util.ExceptionStatus;
import com.text.chat.util.PayloadValidator;
import com.text.chat.util.ResponseUtil;
import com.text.chat.response.UserControllerResp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    IUserService userService;

    @PostMapping(path = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserControllerResp createUser(@RequestBody User payload) throws ApplicationException {
        PayloadValidator.validateUser(payload, true, false);

        LOGGER.info("User {} registration requested", payload.getId());
        LOGGER.debug("Calling service layer for creating user {}", payload.getId());
        User user = userService.create(payload);

        return ResponseUtil.addUserToUserResp(user, ApplicationStatus.CREATED_SUCCESSFULLY);
    }

    @GetMapping(path = "get")
    public UserControllerResp getUser(@RequestBody User payload) throws ApplicationException {
        PayloadValidator.validateUser(payload);

        LOGGER.info("User {} details requested by {}", payload.getId(), payload.getRequestedBy().getId());
        LOGGER.debug("Calling service layer for fetching user {} details", payload.getId());
        User user = userService.get(payload);
        return ResponseUtil.addUserToUserResp(user, ApplicationStatus.FETCHED_SUCCESSFULLY);
    }

    @PostMapping(path = "update")
    public UserControllerResp updateUser(@RequestBody User payload) throws ApplicationException {
        PayloadValidator.validateUser(payload);

        LOGGER.info("User {} requesting for updating info", payload.getId());

        if (!payload.getId().equals(payload.getRequestedBy().getId())) {
            LOGGER.info("User {} requesting update for other user {}", payload.getRequestedBy().getId(),
                    payload.getId());
            throw new ApplicationException(ExceptionStatus.USER_NOT_AUTHORIZED);
        }


        LOGGER.debug("Calling service layer for user {} update", payload.getId());
        User updatedUser = userService.update(payload);
        return ResponseUtil.addUserToUserResp(updatedUser, ApplicationStatus.UPDATED_SUCCESSFULLY);
    }
}
