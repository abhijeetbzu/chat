package com.text.chat.service.impl;

import com.text.chat.dao.IUserDao;
import com.text.chat.exception.ApplicationException;
import com.text.chat.exception.UserNotFoundException;
import com.text.chat.model.User;
import com.text.chat.service.IUserService;
import com.text.chat.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class IUserServiceImpl implements IUserService {

    private static final Logger LOGGER = LogManager.getLogger(IUserServiceImpl.class);

    @Autowired
    IUserDao userDao;

    @Override
    public User create(User user) throws ApplicationException {
        if (userDao.isValidUser(user)) {
            LOGGER.info("User {} already exists", user.getId());
            throw new ApplicationException(ExceptionStatus.USER_ALREADY_EXIST);
        }

        User registeredUser = userDao.create(user);
        return registeredUser;
    }

    @Override
    public User get(User user) throws UserNotFoundException {
        return userDao.get(user);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }
}
