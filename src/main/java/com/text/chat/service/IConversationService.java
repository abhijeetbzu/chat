package com.text.chat.service;

import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Conversation;

public interface IConversationService {
    boolean delete(Conversation conversation) throws ApplicationException;
}
