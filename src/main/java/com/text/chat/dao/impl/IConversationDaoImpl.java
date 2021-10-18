package com.text.chat.dao.impl;

import com.text.chat.dao.IConversationDao;
import com.text.chat.entity.*;
import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Conversation;
import com.text.chat.model.Room;
import com.text.chat.util.ExceptionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class IConversationDaoImpl implements IConversationDao {

    private static final Logger LOGGER = LogManager.getLogger(IConversationDaoImpl.class);

    @PersistenceContext
    EntityManager em;

    private ConversationEntity getConversationEntity(Conversation conversation) throws ApplicationException {
        return getConversationEntity(conversation, true);
    }

    private ConversationEntity getConversationEntity(Conversation conversation, boolean throwException) throws ApplicationException {
        ConversationEntity conversationEntity = em.find(ConversationEntity.class, conversation.getId());
        if (conversationEntity == null && throwException) {
            LOGGER.info("Message {} doesn't exist", conversation.getId());
            throw new ApplicationException(ExceptionStatus.CONVERSATION_NOT_FOUND);
        }
        return conversationEntity;
    }

    @Override
    public boolean add(MessageEntity msgEntity, Room room) throws ApplicationException {
        RoomEntity roomEntity = em.find(RoomEntity.class, room.getId());
        if (roomEntity == null) {
            LOGGER.info("{} room id doesn't exist", room.getId());
            throw new ApplicationException(ExceptionStatus.ROOM_NOT_FOUND);
        }
        if (msgEntity.getUserEntity() == null) {
            LOGGER.info("No user id associated with message {}", msgEntity.getId());
            throw new ApplicationException(ExceptionStatus.UNEXPECTED_ERROR);
        }

        TypedQuery<UserEntity> query =
                em.createQuery("select a.userEntity from room_user_mapping a where a.roomEntity.id = :roomId",
                        UserEntity.class);
        query.setParameter("roomId", room.getId());

        List<UserEntity> users = query.getResultList();
        for (UserEntity user : users) {
            LOGGER.info("Adding message {} to the user {} for the room {}", msgEntity.getId(), user.getId(),
                    room.getId());
            ConversationEntity conversationEntity = new ConversationEntity();
            conversationEntity.setUserEntity(user);
            conversationEntity.setMessageEntity(msgEntity);
            conversationEntity.setRoomEntity(roomEntity);
            int type = user.getId().equals(msgEntity.getUserEntity().getId()) ? 1 : 0;
            conversationEntity.setType(type);
            em.persist(conversationEntity);
        }

        return true;
    }

    @Override
    public boolean delete(Conversation conversation) throws ApplicationException {
        ConversationEntity conversationEntity = getConversationEntity(conversation);
        if (conversationEntity.getUserEntity() == null) {
            LOGGER.info("No user id associated with conversation {}", conversation.getId());
            throw new ApplicationException(ExceptionStatus.UNEXPECTED_ERROR);
        }
        if (!conversationEntity.getUserEntity().getId().equals(conversation.getRequestedBy().getId())) {
            LOGGER.info("User {} not authorized to delete conversation {}", conversation.getRequestedBy().getId(),
                    conversation.getId());
            throw new ApplicationException(ExceptionStatus.USER_NOT_AUTHORIZED);
        }

        em.remove(conversationEntity);
        return true;
    }
}
