package com.vashchenko.cleverdev_test_task.service;

import com.vashchenko.cleverdev_test_task.entity.Note;
import com.vashchenko.cleverdev_test_task.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NoteService {
    private final NoteRepository repository;
    public Note findNoteByPatientGuidAndUserLoginAndCreatedDateTime(UUID guid, String login, LocalDateTime dob) {
        return repository.findNoteByPatientGuidAndUserLoginAndCreatedDateTime(guid.toString(),login,dob);
    }

    public Note save(Note note) {
        return repository.save(note);
    }
}
