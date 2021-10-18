package com.text.chat.dao;

import com.text.chat.entity.RoomEntity;
import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Room;

public interface IRoomDao {
    RoomEntity create(Room room);

    Room get(Room room) throws ApplicationException;

    boolean delete(Room room) throws ApplicationException;

    Room update(Room room) throws ApplicationException;
}
