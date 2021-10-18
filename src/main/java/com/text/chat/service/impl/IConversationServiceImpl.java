package com.text.chat.service.impl;

import com.text.chat.dao.IConversationDao;
import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Conversation;
import com.text.chat.service.IConversationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class IConversationServiceImpl implements IConversationService {

    private static final Logger LOGGER = LogManager.getLogger(IConversationServiceImpl.class);

    @Autowired
    IConversationDao conversationDao;

    @Override
    public boolean delete(Conversation conversation) throws ApplicationException {
        return conversationDao.delete(conversation);
    }
}
