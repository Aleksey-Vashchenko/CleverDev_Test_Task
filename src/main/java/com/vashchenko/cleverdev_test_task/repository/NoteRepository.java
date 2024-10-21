package com.vashchenko.cleverdev_test_task.repository;

import com.vashchenko.cleverdev_test_task.entity.Note;
import com.vashchenko.cleverdev_test_task.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface NoteRepository extends CrudRepository<Note,Long> {

    @Query("SELECT n FROM Note n where n.patient.oldClientGuids like %:guid% " +
            "and n.creator.login=:login " +
            "and n.createdAt=:createdDateTime")
    Note findNoteByPatientGuidAndUserLoginAndCreatedDateTime(UUID guid, String login, LocalDateTime createdDateTime);
}
