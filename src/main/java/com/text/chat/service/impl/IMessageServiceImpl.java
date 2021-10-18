package com.text.chat.service.impl;

import com.text.chat.dao.IConversationDao;
import com.text.chat.dao.IMessageDao;
import com.text.chat.dao.IRoomUserMappingDao;
import com.text.chat.entity.MessageEntity;
import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Message;
import com.text.chat.model.Room;
import com.text.chat.service.IMessageService;
import com.text.chat.util.ExceptionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class IMessageServiceImpl implements IMessageService {
    private static final Logger LOGGER = LogManager.getLogger(IMessageServiceImpl.class);

    @Autowired
    private IMessageDao messageDao;

    @Autowired
    private IRoomUserMappingDao roomUserMappingDao;

    @Autowired
    private IConversationDao conversationDao;

    @Override
    public boolean delete(Message msg) throws ApplicationException {
        return messageDao.delete(msg);
    }

    @Override
    public boolean send(Message msg) throws ApplicationException {
        Room room = msg.getRoom();
        if (!roomUserMappingDao.isUserAuthorized(room)) {
            LOGGER.info("User {} and Group {} not associated with each other",
                    room.getRequestedBy().getId(), room.getId());
            throw new ApplicationException(ExceptionStatus.USER_NOT_AUTHORIZED);
        }
        MessageEntity messageEntity = messageDao.add(msg);
        return conversationDao.add(messageEntity, msg.getRoom());
    }

    @Override
    public List<Message> get(Room room) throws ApplicationException {
        if (!roomUserMappingDao.isUserAuthorized(room)) {
            LOGGER.info("User {} and Group {} not associated with each other",
                    room.getRequestedBy().getId(), room.getId());
            throw new ApplicationException(ExceptionStatus.USER_NOT_AUTHORIZED);
        }
        return messageDao.get(room);
    }
}
