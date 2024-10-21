package com.vashchenko.oldsystem.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Client {
    private UUID guid;
    private String agency;
    private String firstName;
    private String lastName;
    private String status;
    private LocalDate dob;
    private LocalDateTime createdAt;

    public Client() {
    }

    public Client(UUID guid, String agency, String firstName, String lastName, String status, LocalDate dob, LocalDateTime createdAt) {
        this.guid = guid;
        this.agency = agency;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.dob = dob;
        this.createdAt = createdAt;
    }

    public String getAgency() {
        return agency;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDob() {
        return dob;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getGuid() {
        return guid;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
