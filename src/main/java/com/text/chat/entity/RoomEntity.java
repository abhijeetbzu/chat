package com.text.chat.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserEntity userEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomEntity", cascade = CascadeType.REMOVE)
    private List<ConversationEntity> conversationEntities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomEntity", cascade = CascadeType.REMOVE)
    private List<RoomUserMappingEntity> roomUserMappingEntities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
