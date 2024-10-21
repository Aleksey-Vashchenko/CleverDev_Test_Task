package com.vashchenko.cleverdev_test_task.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "created_date_time",nullable = false)
    LocalDateTime createdAt;
    @Column(name = "last_modified_date_time",nullable = false)
    LocalDateTime lastModifiedAt;

    @OneToOne
            @JoinColumn(name = "id")
    User creator;
    @OneToOne
    @JoinColumn(name = "id")
    User lastModifier;
    String note;
    @OneToOne
    @JoinColumn(name = "id")
    Patient patient;
}
