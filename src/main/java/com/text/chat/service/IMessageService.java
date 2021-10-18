package com.text.chat.service;

import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Message;
import com.text.chat.model.Room;

import java.util.List;

public interface IMessageService {
    boolean delete(Message msg) throws ApplicationException;

    boolean send(Message msg) throws ApplicationException;

    List<Message> get(Room userGroup) throws ApplicationException;
}
