package com.text.chat.dao;

import com.text.chat.entity.MessageEntity;
import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Message;
import com.text.chat.model.Room;

import java.util.List;

public interface IMessageDao {
    boolean delete(Message msg) throws ApplicationException;

    MessageEntity add(Message msg);

    List<Message> get(Room room) throws ApplicationException;
}
