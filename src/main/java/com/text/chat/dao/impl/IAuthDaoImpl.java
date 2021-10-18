package com.text.chat.dao.impl;

import com.text.chat.dao.IAuthDao;
import com.text.chat.entity.UserEntity;
import com.text.chat.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class IAuthDaoImpl implements IAuthDao {
    private static final Logger LOGGER = LogManager.getLogger(IUserDaoImpl.class);

    @PersistenceContext
    private EntityManager em;

    private User loadEntityIntoModel(UserEntity userEntity) {
        User user = new User();
        user.setName(userEntity.getName());
        user.setId(userEntity.getId());
        user.setPassword(userEntity.getPassword());
        return user;
    }

    @Override
    public User fetch(User user) throws UsernameNotFoundException {
        LOGGER.debug("Fetching user {} details", user.getId());
        UserEntity userEntity = em.find(UserEntity.class, user.getId());
        if (userEntity == null) {
            throw new UsernameNotFoundException("User " + user.getId() + " doesn't exist in the system");
        }
        return loadEntityIntoModel(userEntity);
    }
}
