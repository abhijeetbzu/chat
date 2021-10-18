package com.text.chat.controller;

import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Message;
import com.text.chat.model.Room;
import com.text.chat.service.IMessageService;
import com.text.chat.response.MessageControllerResp;
import com.text.chat.util.ApplicationStatus;
import com.text.chat.util.PayloadValidator;
import com.text.chat.util.ResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {

    private static final Logger LOGGER = LogManager.getLogger(MessageController.class);

    @Autowired
    private IMessageService msgService;

    @PostMapping("send")
    public MessageControllerResp send(@RequestBody Message msg) throws ApplicationException {
        PayloadValidator.validateMessage(msg, false);
        PayloadValidator.validateRoom(msg.getRoom(), true, false);

        LOGGER.info("Message {} being sent by {} for room {}", msg.getId(), msg.getRequestedBy().getId(),
                msg.getRoom().getId());
        msg.getRoom().setRequestedBy(msg.getRequestedBy());


        LOGGER.debug("Calling service layer for sending message {}", msg.getId());
        msgService.send(msg);
        return ResponseUtil.formMsgResp(ApplicationStatus.SENT_SUCCESSFULLY);
    }

    @DeleteMapping("delete")
    public MessageControllerResp delete(@RequestBody Message msg) throws ApplicationException {
        PayloadValidator.validateMessage(msg);

        LOGGER.info("Message {} deletion requested by {}", msg.getId(), msg.getRequestedBy().getId());
        LOGGER.debug("Calling service layer for deleting message {}", msg.getId());
        msgService.delete(msg);
        return ResponseUtil.formMsgResp(ApplicationStatus.DELETED_SUCCESSFULLY);
    }

    @GetMapping("get")
    public MessageControllerResp get(@RequestBody Message message) throws ApplicationException {
        PayloadValidator.validateMessage(message, false);
        PayloadValidator.validateRoom(message.getRoom(), true, false);

        Room room = message.getRoom();
        room.setRequestedBy(message.getRequestedBy());

        LOGGER.info("Fetching messages for room {}", room.getId());
        LOGGER.debug("Calling service layer for fetching messages for room {}", room.getId());
        List<Message> messages = msgService.get(room);
        return ResponseUtil.addMessagesToResp(messages, ApplicationStatus.FETCHED_SUCCESSFULLY);
    }
}
