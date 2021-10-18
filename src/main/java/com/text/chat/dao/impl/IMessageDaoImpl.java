package com.text.chat.dao.impl;

import com.text.chat.dao.IMessageDao;
import com.text.chat.entity.MessageEntity;
import com.text.chat.entity.UserEntity;
import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Message;
import com.text.chat.model.Room;
import com.text.chat.model.User;
import com.text.chat.util.DateUtil;
import com.text.chat.util.ExceptionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class IMessageDaoImpl implements IMessageDao {

    private static final Logger LOGGER = LogManager.getLogger(IMessageDaoImpl.class);

    @PersistenceContext
    EntityManager em;

    private MessageEntity getMessageEntity(Message message) throws ApplicationException {
        return getMessageEntity(message, true);
    }

    private MessageEntity getMessageEntity(Message message, boolean throwException) throws ApplicationException {
        MessageEntity messageEntity = em.find(MessageEntity.class, message.getId());
        if (messageEntity == null && throwException) {
            LOGGER.info("Message {} doesn't exist", message.getId());
            throw new ApplicationException(ExceptionStatus.MESSAGE_NOT_FOUND);
        }
        return messageEntity;
    }

    private MessageEntity loadModelIntoEntityForInsert(Message message) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setContent(message.getContent());
        messageEntity.setMedia(message.getMedia());

        UserEntity userEntity = em.find(UserEntity.class, message.getRequestedBy().getId());
        messageEntity.setUserEntity(userEntity);

        return messageEntity;
    }

    private Message transformEntityToModel(MessageEntity messageEntity) throws ApplicationException {
        UserEntity userEntity = messageEntity.getUserEntity();
        if (userEntity == null) {
            LOGGER.info("No user id associated with message {}", messageEntity.getId());
            throw new ApplicationException(ExceptionStatus.UNEXPECTED_ERROR);
        }
        Message message = new Message();
        message.setContent(messageEntity.getContent());
        message.setMedia(messageEntity.getMedia());

        User user = new User();
        user.setName(userEntity.getName());
        user.setId(userEntity.getId());
        message.setUser(user);

        return message;
    }

    private boolean isEligibleForDeletion(Date createDate) {
        return DateUtil.diffInMin(createDate, new Date()) <= 15;
    }

    @Override
    public boolean delete(Message msg) throws ApplicationException {
        LOGGER.info("Deleting message having id: {}", msg.getId());
        MessageEntity entity = getMessageEntity(msg);
        if (entity.getUserEntity() == null) {
            LOGGER.info("No user id associated with message {}", msg.getId());
            throw new ApplicationException(ExceptionStatus.UNEXPECTED_ERROR);
        }
        if (!entity.getUserEntity().getId().equals(msg.getRequestedBy().getId())) {
            LOGGER.info("User {} not authorized to delete message {}", msg.getRequestedBy().getId(), msg.getId());
            throw new ApplicationException(ExceptionStatus.USER_NOT_AUTHORIZED);
        }
        boolean eligibleForDeletion = isEligibleForDeletion(entity.getCreateDate());
        if (!eligibleForDeletion) {
            LOGGER.info("User {} surpasses the min time for deleting message {}", msg.getRequestedBy().getId(),
                    msg.getId());
            throw new ApplicationException(ExceptionStatus.USER_NOT_ELIGIBLE);
        }

        em.remove(entity);
        return true;
    }

    @Override
    public MessageEntity add(Message msg) {
        MessageEntity entity = loadModelIntoEntityForInsert(msg);
        em.persist(entity);

        return entity;
    }

    @Override
    public List<Message> get(Room room) throws ApplicationException {
        TypedQuery<MessageEntity> query = em.createQuery(
                "select a.messageEntity from conversation a where a.roomEntity.id = :roomId and a.userEntity.id = :userId"
                , MessageEntity.class);
        query.setParameter("roomId", room.getId());
        query.setParameter("userId", room.getRequestedBy().getId());

        List<MessageEntity> messageEntities = query.getResultList();
        List<Message> messages = new ArrayList<>();

        for (MessageEntity messageEntity : messageEntities) {
            Message message = transformEntityToModel(messageEntity);
            messages.add(message);
        }

        return messages;
    }
}
