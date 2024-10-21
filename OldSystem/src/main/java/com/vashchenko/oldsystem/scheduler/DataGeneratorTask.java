package com.vashchenko.oldsystem.scheduler;

import com.vashchenko.oldsystem.entity.Client;
import com.vashchenko.oldsystem.entity.Note;
import com.vashchenko.oldsystem.repository.ClientRepository;
import com.vashchenko.oldsystem.repository.NotesRepository;
import org.instancio.Instancio;
import org.instancio.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DataGeneratorTask {
    private final Logger logger = LoggerFactory.getLogger(DataGeneratorTask.class);
    private final static Random random = new Random();
    private final ClientRepository clientRepository;
    private final NotesRepository notesRepository;

    public DataGeneratorTask(ClientRepository clientRepository, NotesRepository notesRepository) {
        this.clientRepository = clientRepository;
        this.notesRepository = notesRepository;
    }

    @Scheduled
    public void generateData() {
        logger.debug("Start generate task");
        int randomNumber = random.nextInt(3);
        switch (randomNumber){
            case 0:{createNewClient();}
            case 1:{createNewNote();}
            case 2:{updateExistedNote();}
        }
    }

    private void updateExistedNote() {
        logger.debug("updateExistedNote task started");
        Client client = getRandomClient();
        List<Note> matchingNotes = notesRepository.getAllNotes().stream()
                .filter(note -> note.getGuid().equals(client.getGuid()))
                .toList();
        if (!matchingNotes.isEmpty()) {
            Random random = new Random();
            Note randomNote = matchingNotes.get(random.nextInt(matchingNotes.size()));
            randomNote.setComments("Updated Comment");
            randomNote.setModifiedAt(LocalDateTime.now());
            logger.debug("Updated 1 note for client {}",client);
        }
        else {
            logger.debug("Not found notes for client {}",client);
        }
    }

    private void createNewClient(){
        logger.debug("createNewClient task started");
        Client client = Instancio.of(Client.class)
                .set(Select.field(Client::getAgency), "Agency " + (int) (Math.random() * 6))
                .set(Select.field(Client::getStatus), "ACTIVE") // Фиксированный статус
                .generate(Select.field(Client::getDob), gen -> gen.temporal().localDate().range(LocalDate.now().minusYears(70),LocalDate.now().minusYears(5)))
                .generate(Select.field(Client::getCreatedAt), gen -> gen.temporal().localDateTime().range(LocalDateTime.now().minusYears(21),LocalDateTime.now()))
                .create();
        clientRepository.add(client);
        logger.debug("client {} was added",client);
    }

    private void createNewNote(){
        logger.debug("createNewNote task started");
        Client client = getRandomClient();
        Note note = Instancio.of(Note.class)
                .generate(Select.field(Note::getComments),gen -> gen.text().loremIpsum() )
                .generate(Select.field(Note::getModifiedAt), gen -> gen.temporal().localDate().range(LocalDate.now().minusYears(19),LocalDate.now()))
                .set(Select.field(Note::getClientGuid), UUID.randomUUID())
                .generate(Select.field(Note::getDatetime), gen -> gen.temporal().localDate().past())
                .set(Select.field(Note::getLoggedUser), "User" + (int) (Math.random() * 100))
                .generate(Select.field(Note::getCreatedAt), gen -> gen.temporal().localDate().range(LocalDate.now().minusYears(20),LocalDate.now().minusYears(5)))
                .create();
        note.setClientGuid(client.getGuid());
        notesRepository.addNote(note);
        logger.debug("note {} for client {} was added",note,client);

    }

    private Client getRandomClient(){
        int randomClientNumber = random.nextInt(clientRepository.getClientAmount());
        return clientRepository.getClientByIndex(randomClientNumber);
    }
}
