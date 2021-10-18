package com.text.chat.dao;

import com.text.chat.entity.MessageEntity;
import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Conversation;
import com.text.chat.model.Room;

public interface IConversationDao {
    boolean add(MessageEntity msg, Room room) throws ApplicationException;

    boolean delete(Conversation conversation) throws ApplicationException;
}
