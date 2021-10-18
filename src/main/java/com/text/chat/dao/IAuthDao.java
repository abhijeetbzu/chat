package com.text.chat.dao;

import com.text.chat.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IAuthDao {
    User fetch(User user) throws UsernameNotFoundException;
}
