package com.text.chat.service;

import com.text.chat.exception.ApplicationException;
import com.text.chat.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAuthService extends UserDetailsService {
    //boolean validateUser(User user);

    //boolean validateUser(User user, boolean throwException) throws ApplicationException;
}
