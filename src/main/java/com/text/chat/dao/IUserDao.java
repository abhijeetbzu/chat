package com.text.chat.dao;

import com.text.chat.exception.ApplicationException;
import com.text.chat.exception.UserNotFoundException;
import com.text.chat.model.User;

public interface IUserDao {
    User create(User user) throws ApplicationException;

    User get(User user) throws UserNotFoundException;

    boolean isValidUser(User user);

    User update(User user);
}
