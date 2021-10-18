package com.text.chat.dao.impl;

import com.text.chat.dao.IUserDao;
import com.text.chat.entity.UserEntity;
import com.text.chat.exception.ApplicationException;
import com.text.chat.exception.UserNotFoundException;
import com.text.chat.model.User;
import com.text.chat.util.CryptoUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class IUserDaoImpl implements IUserDao {

    private static final Logger LOGGER = LogManager.getLogger(IUserDaoImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CryptoUtil cryptoUtil;

    private User loadEntityIntoModel(UserEntity userEntity) {
        User user = new User();
        user.setName(userEntity.getName());
        user.setId(userEntity.getId());
        return user;
    }

    private UserEntity loadModelIntoEntityForInsert(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setPassword(cryptoUtil.encodeString(user.getPassword()));
        userEntity.setId(user.getId());
        return userEntity;
    }

    private void loadModelIntoEntityForUpdate(UserEntity userEntity, User user) {
        if (user.getName() != null) userEntity.setName(user.getName());
        if (user.getPassword() != null) userEntity.setPassword(cryptoUtil.encodeString(user.getPassword()));
    }

    @Override
    public User create(User user) throws ApplicationException {
        UserEntity userEntity = loadModelIntoEntityForInsert(user);
        em.persist(userEntity);
        return loadEntityIntoModel(userEntity);
    }

    @Override
    public User get(User user) throws UserNotFoundException {
        UserEntity userEntity = em.find(UserEntity.class, user.getId());
        if (userEntity == null) {
            throw new UserNotFoundException("User " + user.getId() + " doesn't exist in the system");
        }
        return loadEntityIntoModel(userEntity);
    }

    @Override
    public boolean isValidUser(User user) {
        UserEntity userEntity = em.find(UserEntity.class, user.getId());
        return userEntity != null;
    }

    @Override
    public User update(User user) {
        UserEntity userEntity = em.find(UserEntity.class, user.getId());
        loadModelIntoEntityForUpdate(userEntity, user);
        em.merge(userEntity);
        return loadEntityIntoModel(userEntity);
    }
}
