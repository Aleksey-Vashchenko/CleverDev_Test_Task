package com.vashchenko.oldsystem.repository;

import com.vashchenko.oldsystem.entity.Note;
import com.vashchenko.oldsystem.web.dto.GetNotesByClientRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotesRepository {
    private final List<Note> notes = new ArrayList<>();

    public List<Note> findNotesByRequest(GetNotesByClientRequest request) {
        return notes.stream()
                .filter(note -> note.getClientGuid().equals(request.clientGuid()))
                .filter(note -> isWithinDateRange(note, request.dateFrom(), request.dateTo()))
                .collect(Collectors.toList());
    }

    private boolean isWithinDateRange(Note note, LocalDate dateFrom, LocalDate dateTo) {
        LocalDate noteDate = note.getModifiedAt().toLocalDate();
        return (noteDate.isEqual(dateFrom) || noteDate.isAfter(dateFrom)) &&
                (noteDate.isEqual(dateTo) || noteDate.isBefore(dateTo));
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public List<Note> getAllNotes(){
        return new ArrayList<>(notes);
    }
}
