package com.text.chat.service.impl;

import com.text.chat.dao.IRoomDao;
import com.text.chat.dao.IRoomUserMappingDao;
import com.text.chat.entity.RoomEntity;
import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Room;
import com.text.chat.service.IRoomService;
import com.text.chat.util.ExceptionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class IRoomServiceImpl implements IRoomService {

    private static final Logger LOGGER = LogManager.getLogger(IRoomServiceImpl.class);

    @Autowired
    private IRoomDao roomDao;

    @Autowired
    private IRoomUserMappingDao roomUserMappingDao;

    private boolean isEligibleForDeletion(Room room) {
        return isEligibleForUpdate(room);
    }

    private boolean isEligibleForRoomInfo(Room room) {
        return roomUserMappingDao.isUserAuthorized(room);
    }

    private boolean isEligibleForUpdate(Room room) {
        return roomUserMappingDao.isUserAuthorized(room, true);
    }

    private Room loadEntityIntoModel(RoomEntity roomEntity) {
        Room room = new Room();
        room.setName(roomEntity.getName());
        room.setId(roomEntity.getId());
        return room;
    }


    @Override
    public Room create(Room room) throws ApplicationException {
        RoomEntity roomEntity = roomDao.create(room);
        roomUserMappingDao.addUserToRoom(room, roomEntity);
        return loadEntityIntoModel(roomEntity);
    }

    @Override
    public Room get(Room room) throws ApplicationException {
        boolean eligibleForRoomInfo = isEligibleForRoomInfo(room);
        if (!eligibleForRoomInfo) {
            LOGGER.info("User {} not authorized for getting room {} info", room.getRequestedBy().getId(),
                    room.getId());
            throw new ApplicationException(ExceptionStatus.USER_NOT_AUTHORIZED);
        }
        return roomDao.get(room);
    }

    @Override
    public boolean delete(Room room) throws ApplicationException {
        boolean eligibleForDeletion = isEligibleForDeletion(room);
        if (!eligibleForDeletion) {
            LOGGER.info("User {} not authorized for deleting room {}", room.getRequestedBy().getId(),
                    room.getId());
            throw new ApplicationException(ExceptionStatus.USER_NOT_AUTHORIZED);
        }
        return roomDao.delete(room);
    }

    @Override
    public Room update(Room room) throws ApplicationException {
        boolean eligibleForDeletion = isEligibleForUpdate(room);
        if (!eligibleForDeletion) {
            LOGGER.info("User {} not authorized for updating room {}", room.getRequestedBy().getId(),
                    room.getId());
            throw new ApplicationException(ExceptionStatus.USER_NOT_AUTHORIZED);
        }
        return roomDao.update(room);
    }

    @Override
    public boolean removeUsersFromRoom(Room room) throws ApplicationException {
        if (!roomUserMappingDao.isUserAuthorized(room, true)) {
            LOGGER.info("User {} not authorized for removing other user from room {}",
                    room.getRequestedBy().getId(), room.getId());
            throw new ApplicationException(ExceptionStatus.USER_NOT_AUTHORIZED);
        }
        return roomUserMappingDao.removeUsersFromRoom(room);
    }

    @Override
    public void exitFromRoom(Room room) {
        roomUserMappingDao.exitFromRoom(room);
    }
}
