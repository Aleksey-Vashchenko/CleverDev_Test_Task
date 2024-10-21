package com.vashchenko.oldsystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;


public class Note {
    private String comments;
    private UUID guid;
    @JsonProperty("modifiedDateTime")
    private LocalDateTime modifiedAt;
    private UUID clientGuid;
    private LocalDateTime datetime;
    private String loggedUser;
    @JsonProperty("createdDateTime")
    private LocalDateTime createdAt;

    public Note() {
    }

    public Note(String comments, UUID guid, LocalDateTime modifiedAt, UUID clientGuid, LocalDateTime datetime, String loggedUser, LocalDateTime createdAt) {
        this.comments = comments;
        this.guid = guid;
        this.modifiedAt = modifiedAt;
        this.clientGuid = clientGuid;
        this.datetime = datetime;
        this.loggedUser = loggedUser;
        this.createdAt = createdAt;
    }

    public String getComments() {
        return comments;
    }

    public UUID getGuid() {
        return guid;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public UUID getClientGuid() {
        return clientGuid;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setClientGuid(UUID clientGuid) {
        this.clientGuid = clientGuid;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
