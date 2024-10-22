package com.vashchenko.cleverdev_test_task.repository;

import com.vashchenko.cleverdev_test_task.entity.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface NoteRepository extends CrudRepository<Note,Long> {

    @Query("SELECT n FROM Note n WHERE n.patient.oldClientGuids LIKE %:guid% " +
            "AND n.creator.login = :login " +
            "AND n.createdAt = :createdDateTime")
    Note findNoteByPatientGuidAndUserLoginAndCreatedDateTime(@Param("guid") String guid,
                                                             @Param("login") String login,
                                                             @Param("createdDateTime") LocalDateTime createdDateTime);}
