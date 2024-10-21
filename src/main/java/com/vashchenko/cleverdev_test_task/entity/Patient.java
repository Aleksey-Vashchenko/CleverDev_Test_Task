package com.vashchenko.cleverdev_test_task.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "first_name",nullable = false)
    String firstName;
    @Column(name = "last_name",nullable = false)
    String lastName;
    @Column(name = "old_client_guid",nullable = false)
    String oldClientGuids;
    @Column(name = "status_id",nullable = false)
    Short statusId;
}
