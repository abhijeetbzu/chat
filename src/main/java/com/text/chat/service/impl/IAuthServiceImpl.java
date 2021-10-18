package com.text.chat.service.impl;

import com.text.chat.dao.IAuthDao;
import com.text.chat.model.User;
import com.text.chat.service.IAuthService;
import com.text.chat.util.UserInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class IAuthServiceImpl implements IAuthService {
    private static final Logger LOGGER = LogManager.getLogger(IAuthServiceImpl.class);

    @Autowired
    IAuthDao authDao;

    private UserDetails loadUserIntoUserDetails(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setPassword(user.getPassword());
        userInfo.setUsername(user.getId());
        return userInfo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Calling service layer for fetching user {} details for authentication", username);
        User user = authDao.fetch(new User(username));
        return loadUserIntoUserDetails(user);
    }
}
