package com.text.chat.dao.impl;

import com.text.chat.dao.IRoomDao;
import com.text.chat.entity.RoomEntity;
import com.text.chat.entity.UserEntity;
import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Room;
import com.text.chat.util.ExceptionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class IRoomDaoImpl implements IRoomDao {

    private static final Logger LOGGER = LogManager.getLogger(IRoomDaoImpl.class);

    @PersistenceContext
    EntityManager em;

    private RoomEntity getRoomEntity(Room room) throws ApplicationException {
        return getRoomEntity(room, true);
    }

    private RoomEntity getRoomEntity(Room room, boolean throwException) throws ApplicationException {
        RoomEntity roomEntity = em.find(RoomEntity.class, room.getId());
        if (roomEntity == null && throwException) {
            LOGGER.info("Room {} doesn't exist", room.getId());
            throw new ApplicationException(ExceptionStatus.ROOM_NOT_FOUND);
        }
        return roomEntity;
    }

    private void loadModelIntoEntityForUpdate(RoomEntity entity, Room room) {
        if (room.getName() != null) entity.setName(room.getName());
    }

    private RoomEntity loadModelIntoEntityForInsert(Room room){
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setName(room.getName());

        UserEntity userEntity = em.find(UserEntity.class, room.getRequestedBy().getId());
        roomEntity.setUserEntity(userEntity);
        return roomEntity;
    }

    private Room loadEntityIntoModel(RoomEntity entity) {
        Room room = new Room();
        room.setName(entity.getName());
        room.setId(entity.getId());
        return room;
    }

    @Override
    public RoomEntity create(Room room){
        RoomEntity roomEntity = loadModelIntoEntityForInsert(room);
        em.persist(roomEntity);
        return roomEntity;
    }

    @Override
    public Room get(Room room) throws ApplicationException {
        RoomEntity roomEntity = getRoomEntity(room);
        return loadEntityIntoModel(roomEntity);
    }

    @Override
    public boolean delete(Room room) throws ApplicationException {
        RoomEntity roomEntity = getRoomEntity(room);
        em.remove(roomEntity);
        return true;
    }

    @Override
    public Room update(Room room) throws ApplicationException {
        RoomEntity roomEntity = getRoomEntity(room);
        loadModelIntoEntityForUpdate(roomEntity, room);
        roomEntity = em.merge(roomEntity);
        return loadEntityIntoModel(roomEntity);
    }
}
