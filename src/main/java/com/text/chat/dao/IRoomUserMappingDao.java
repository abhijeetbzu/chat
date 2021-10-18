package com.text.chat.dao;

import com.text.chat.entity.RoomEntity;
import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Room;

public interface IRoomUserMappingDao {
    boolean isUserAuthorized(Room room);

    boolean isUserAuthorized(Room room, boolean forUpdate);

    boolean addUserToRoom(Room room, RoomEntity roomEntity) throws ApplicationException;

    boolean removeUsersFromRoom(Room room);

    void exitFromRoom(Room room);
}
