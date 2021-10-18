package com.text.chat.service;

import com.text.chat.exception.ApplicationException;
import com.text.chat.exception.UserNotFoundException;
import com.text.chat.model.User;

public interface IUserService {
    User create(User user) throws ApplicationException;

    User get(User user) throws UserNotFoundException;

    User update(User user);
}
