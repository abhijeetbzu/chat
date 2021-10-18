package com.text.chat.dao.impl;

import com.text.chat.dao.IRoomUserMappingDao;
import com.text.chat.entity.RoomEntity;
import com.text.chat.entity.RoomUserMappingEntity;
import com.text.chat.entity.UserEntity;
import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Room;
import com.text.chat.model.User;
import com.text.chat.util.ExceptionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class IRoomUserMappingDaoImpl implements IRoomUserMappingDao {

    private static final Logger LOGGER = LogManager.getLogger(IRoomUserMappingDaoImpl.class);

    @PersistenceContext
    EntityManager em;

    private void removeUserFromRoom(User user, Room room) {
        TypedQuery<RoomUserMappingEntity> query = em.createQuery(
                "select a from room_user_mapping  a where a.userEntity.id = :userId and a.roomEntity.id = :roomId"
                , RoomUserMappingEntity.class);
        query.setParameter("userId", user.getId());
        query.setParameter("roomId", room.getId());
        RoomUserMappingEntity roomUserMappingEntity = query.getSingleResult();
        em.remove(roomUserMappingEntity);
    }

    @Override
    public boolean isUserAuthorized(Room room) {
        return isUserAuthorized(room, false);
    }

    @Override
    public boolean isUserAuthorized(Room room, boolean forUpdate) {
        String query = "select a from room_user_mapping a where a.roomEntity.id = :roomId and a.userEntity.id = :userId";
        if (forUpdate) {
            query += " and a.admin = 1";
        }
        TypedQuery<RoomUserMappingEntity> typedQuery = em.createQuery(query, RoomUserMappingEntity.class);
        typedQuery.setParameter("roomId", room.getId());
        typedQuery.setParameter("userId", room.getRequestedBy().getId());

        return !typedQuery.getResultList().isEmpty();
    }

    @Override
    public boolean addUserToRoom(Room room, RoomEntity roomEntity) throws ApplicationException {
        List<User> users = room.getUsers();
        boolean validUserPresent = false;
        for (User user : users) {
            UserEntity userEntity = em.find(UserEntity.class, user.getId());
            if (userEntity == null) {
                LOGGER.info("User {} doesn't exist", user.getId());
                continue;
            }
            validUserPresent = true;
            RoomUserMappingEntity roomUserMappingEntity = new RoomUserMappingEntity();
            roomUserMappingEntity.setUserEntity(userEntity);
            roomUserMappingEntity.setRoomEntity(roomEntity);
            roomUserMappingEntity.setAdmin(user.getAdmin());
            em.persist(roomUserMappingEntity);
        }
        //for rolling back the transaction and deleting the room which was created initially
        if (!validUserPresent) {
            LOGGER.info("No valid user provided for the room {}", roomEntity.getId());
            throw new ApplicationException(ExceptionStatus.UNEXPECTED_ERROR);
        }
        return true;
    }

    @Override
    public boolean removeUsersFromRoom(Room room){
        for (User user : room.getUsers()) {
            removeUserFromRoom(user, room);
        }
        return true;
    }

    @Override
    public void exitFromRoom(Room room){
        removeUserFromRoom(room.getRequestedBy(), room);
    }
}
