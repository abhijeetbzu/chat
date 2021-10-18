package com.text.chat.response;

import com.text.chat.model.Room;

public class RoomControllerResp extends ApplicationResp {
    private Room room;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
