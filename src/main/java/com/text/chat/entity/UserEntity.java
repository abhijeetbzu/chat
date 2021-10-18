package com.text.chat.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "user")
public class UserEntity {
    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity", cascade = CascadeType.REMOVE)
    private List<ConversationEntity> conversationEntities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity", cascade = CascadeType.REMOVE)
    private List<RoomUserMappingEntity> roomUserMappingEntities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity", cascade = CascadeType.REMOVE)
    private List<MessageEntity> messageEntities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity")
    private List<RoomEntity> roomEntities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ConversationEntity> getConversationEntities() {
        return conversationEntities;
    }

    public void setConversationEntities(List<ConversationEntity> conversationEntities) {
        this.conversationEntities = conversationEntities;
    }

    public List<RoomUserMappingEntity> getRoomUserMappingEntities() {
        return roomUserMappingEntities;
    }

    public void setRoomUserMappingEntities(List<RoomUserMappingEntity> roomUserMappingEntities) {
        this.roomUserMappingEntities = roomUserMappingEntities;
    }

    public List<MessageEntity> getMessageEntities() {
        return messageEntities;
    }

    public void setMessageEntities(List<MessageEntity> messageEntities) {
        this.messageEntities = messageEntities;
    }

    public List<RoomEntity> getRoomEntities() {
        return roomEntities;
    }

    public void setRoomEntities(List<RoomEntity> roomEntities) {
        this.roomEntities = roomEntities;
    }
}
