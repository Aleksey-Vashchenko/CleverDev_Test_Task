package com.vashchenko.oldsystem;

import com.vashchenko.oldsystem.entity.Client;
import com.vashchenko.oldsystem.entity.Note;
import com.vashchenko.oldsystem.repository.ClientRepository;
import com.vashchenko.oldsystem.repository.NotesRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@EnableScheduling
@Component
public class OldSystemApplication {
    private final ClientRepository clientRepository;
    private final NotesRepository notesRepository;

    public OldSystemApplication(ClientRepository clientRepository, NotesRepository notesRepository) {
        this.clientRepository = clientRepository;
        this.notesRepository = notesRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(OldSystemApplication.class, args);
    }

    @EventListener(value = ApplicationStartedEvent.class)
    public void addSameClientsByDifferentGuid(){
        Client client1 = new Client();
        client1.setAgency("123");
        client1.setDob(LocalDate.of(2000,12,30));
        client1.setGuid(UUID.fromString("8e7e0ebb-0b5d-47c2-b716-d70fed1f7b1c"));
        client1.setStatus("Active");
        client1.setCreatedAt(LocalDateTime.of(2000,12,30,10,10));
        client1.setFirstName("1");
        client1.setLastName("1");
        clientRepository.add(client1);

        Client client2 = new Client();
        client2.setAgency("123");
        client2.setDob(LocalDate.of(2000,12,30));
        client2.setGuid(UUID.fromString("cd4a503d-e057-4571-ae8e-d0a93d34133a"));
        client2.setStatus("Active");
        client2.setCreatedAt(LocalDateTime.of(2000,12,30,10,10));
        client2.setFirstName("1");
        client2.setLastName("2");
        clientRepository.add(client2);

        Note note = new Note();
        note.setGuid(UUID.fromString("55cfeb78-ab67-4488-a0d0-d5c52c25b01c"));
        note.setClientGuid(UUID.fromString("cd4a503d-e057-4571-ae8e-d0a93d34133a"));
        note.setComments("Note from old guid user");
        note.setCreatedAt(LocalDateTime.of(2012,12,12,12,12,12));
        note.setDatetime(LocalDateTime.now());
        note.setLoggedUser("petya123");
        note.setModifiedAt(LocalDateTime.of(2013,12,12,12,12,12));
        notesRepository.addNote(note);
    }

    @EventListener(value = ApplicationStartedEvent.class)
    public void addNoteLikeExistedInNewSystemWithNotExistedUser(){
        Note note = new Note();
        note.setGuid(UUID.fromString("31eade43-3df8-4c16-a371-fe5951e52a0e"));
        note.setClientGuid(UUID.fromString("8e7e0ebb-0b5d-47c2-b716-d70fed1f7b1c"));
        note.setComments("Note like existed");
        note.setCreatedAt(LocalDateTime.of(2012,12,12,12,12,12));
        note.setDatetime(LocalDateTime.now());
        note.setLoggedUser("petya123");
        note.setModifiedAt(LocalDateTime.of(2013,12,12,12,12,12));
        notesRepository.addNote(note);
    }
    @EventListener(value = ApplicationStartedEvent.class)
    public void addNoteLaterThanInNewSystem(){
        Note note = new Note();
        note.setGuid(UUID.fromString("31eade43-3df8-4c16-a371-fe5951e52a6e"));
        note.setClientGuid(UUID.fromString("8e7e0ebb-0b5d-47c2-b716-d70fed1f7b1c"));
        note.setComments("Not Expired");
        note.setCreatedAt(LocalDateTime.of(2015,12,12,12,12,12));
        note.setDatetime(LocalDateTime.now());
        note.setLoggedUser("petya123");
        note.setModifiedAt(LocalDateTime.of(2020,12,12,12,12,12));
        notesRepository.addNote(note);
    }

    @EventListener(value = ApplicationStartedEvent.class)
    public void addNoteEarlierThanInNewSystem(){
        Note note = new Note();
        note.setGuid(UUID.fromString("e21ab01b-4827-4133-89fb-edae7df3c6c0"));
        note.setClientGuid(UUID.fromString("8e7e0ebb-0b5d-47c2-b716-d70fed1f7b1c"));
        note.setComments("Expired");
        note.setCreatedAt(LocalDateTime.of(2009,12,12,12,12,12));
        note.setDatetime(LocalDateTime.now());
        note.setLoggedUser("petya123");
        note.setModifiedAt(LocalDateTime.of(2010,12,12,12,12,12));
        notesRepository.addNote(note);
    }

    @EventListener(value = ApplicationStartedEvent.class)
    public void addNotesAndClientBlockedInNewSystem(){
        Client client1 = new Client();
        client1.setAgency("123");
        client1.setDob(LocalDate.of(2000,12,30));
        client1.setGuid(UUID.fromString("f3a4ebcf-62e4-4d36-8a91-7639f5b8fa59"));
        client1.setStatus("210");
        client1.setCreatedAt(LocalDateTime.of(2000,12,30,10,10));
        client1.setFirstName("3");
        client1.setLastName("3");
        clientRepository.add(client1);

        Note note = new Note();
        note.setGuid(UUID.fromString("1981adc4-d314-4ac0-8a70-47224500a5c0"));
        note.setClientGuid(UUID.fromString("f3a4ebcf-62e4-4d36-8a91-7639f5b8fa59"));
        note.setComments("Note of blocked");
        note.setCreatedAt(LocalDateTime.of(2009,12,12,12,12,12));
        note.setDatetime(LocalDateTime.now());
        note.setLoggedUser("petya123");
        note.setModifiedAt(LocalDateTime.of(2010,12,12,12,12,12));
        notesRepository.addNote(note);
    }

}
