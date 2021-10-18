package com.text.chat.service;

import com.text.chat.exception.ApplicationException;
import com.text.chat.model.Room;

public interface IRoomService {
    Room create(Room room) throws ApplicationException;

    Room get(Room room) throws ApplicationException;

    boolean delete(Room room) throws ApplicationException;

    Room update(Room room) throws ApplicationException;

    boolean removeUsersFromRoom(Room room) throws ApplicationException;

    void exitFromRoom(Room room);
}
