package com.vashchenko.oldsystem.web;

import com.vashchenko.oldsystem.entity.Client;
import com.vashchenko.oldsystem.entity.Note;
import com.vashchenko.oldsystem.repository.NotesRepository;
import com.vashchenko.oldsystem.web.dto.GetNotesByClientRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Component
public class NotesController {
    private final NotesRepository notesRepository;

    public NotesController(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @PostMapping("/notes")
    public List<Note> getNotes(@RequestBody GetNotesByClientRequest request){
        return notesRepository.findNotesByRequest(request);
    }

}
