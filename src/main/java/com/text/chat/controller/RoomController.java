package com.text.chat.controller;

import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Room;
import com.text.chat.response.RoomControllerResp;
import com.text.chat.service.IRoomService;
import com.text.chat.util.ApplicationStatus;
import com.text.chat.util.PayloadValidator;
import com.text.chat.util.ResponseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("room")
public class RoomController {
    private static final Logger LOGGER = LogManager.getLogger(RoomController.class);

    @Autowired
    IRoomService roomService;

    @PostMapping("create")
    public RoomControllerResp createRoom(@RequestBody Room room) throws ApplicationException {
        PayloadValidator.validateRoom(room, false);

        LOGGER.info("Room {} creation requested by {}", room.getId(), room.getRequestedBy().getId());
        LOGGER.debug("Calling service layer for room {} creation", room.getId());
        Room registeredRoom = roomService.create(room);
        return ResponseUtil.addRoomToRoomResp(ApplicationStatus.CREATED_SUCCESSFULLY, registeredRoom);
    }

    @GetMapping("get")
    public RoomControllerResp getRoom(@RequestBody Room room) throws ApplicationException {
        PayloadValidator.validateRoom(room);

        LOGGER.info("Room {} info requested by {}", room.getId(), room.getRequestedBy().getId());
        LOGGER.debug("Calling service layer for room {} info", room.getId());
        Room fetchedRoom = roomService.get(room);
        return ResponseUtil.addRoomToRoomResp(ApplicationStatus.FETCHED_SUCCESSFULLY, fetchedRoom);
    }

    @DeleteMapping("delete")
    public RoomControllerResp deleteRoom(@RequestBody Room room) throws ApplicationException {
        PayloadValidator.validateRoom(room);

        LOGGER.info("Room {} deletion requested by {}", room.getId(), room.getRequestedBy().getId());
        LOGGER.debug("Calling service layer for room {} deletion", room.getId());
        roomService.delete(room);
        return ResponseUtil.formRoomResp(ApplicationStatus.DELETED_SUCCESSFULLY);
    }

    @PostMapping("update")
    public RoomControllerResp updateRoom(@RequestBody Room room) throws ApplicationException {
        PayloadValidator.validateRoom(room);

        LOGGER.info("Room {} details update requested by {}", room.getId(), room.getRequestedBy().getId());
        LOGGER.debug("Calling service layer for room {} update", room.getId());
        roomService.update(room);
        return ResponseUtil.formRoomResp(ApplicationStatus.UPDATED_SUCCESSFULLY);
    }

    @DeleteMapping("removeUsers")
    public RoomControllerResp removeUsers(@RequestBody Room room) throws ApplicationException {
        PayloadValidator.validateRoom(room);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        room.getUsers().forEach(u -> stringBuilder.append(u.getId()).append(", "));
        stringBuilder.append("]");
        LOGGER.info("List of users: {} removal requested for Room {} by {}", stringBuilder.toString(),
                room.getId(), room.getRequestedBy().getId());
        LOGGER.debug("Calling service layer for removing users from room {}", room.getId());
        roomService.removeUsersFromRoom(room);
        return ResponseUtil.formRoomResp(ApplicationStatus.DELETED_SUCCESSFULLY);
    }

    @DeleteMapping("exit")
    public RoomControllerResp exitRoom(@RequestBody Room room) throws ApplicationException {
        PayloadValidator.validateRoom(room);

        LOGGER.info("User {} requested exit from room {}", room.getRequestedBy().getId(), room.getId());
        LOGGER.debug("Calling service layer for exiting room {}", room.getId());
        roomService.exitFromRoom(room);
        return ResponseUtil.formRoomResp(ApplicationStatus.DELETED_SUCCESSFULLY);
    }
}
