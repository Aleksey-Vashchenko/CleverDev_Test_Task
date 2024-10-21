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

    @Scheduled(fixedDelay = 5000)
    public void generateData() {
        logger.info("Start generate task");
        int randomNumber = random.nextInt(3);
        switch (randomNumber){
            case 0:{createNewClient();}
            case 1:{createNewNote();}
            case 2:{updateExistedNote();}
        }
    }

    private void updateExistedNote() {
        logger.info("updateExistedNote task started");
        Client client;
        try {
            client = getRandomClient();

        }
        catch (IllegalArgumentException e){
            return;
        }
        List<Note> matchingNotes = notesRepository.getAllNotes().stream()
                .filter(note -> note.getGuid().equals(client.getGuid()))
                .toList();
        if (!matchingNotes.isEmpty()) {
            Random random = new Random();
            Note randomNote = matchingNotes.get(random.nextInt(matchingNotes.size()));
            randomNote.setComments("Updated Comment");
            randomNote.setModifiedAt(LocalDateTime.now());
            logger.info("Updated 1 note for client {}",client.getGuid());
        }
        else {
            logger.info("Not found notes for client {}",client.getGuid());
        }
    }

    private void createNewClient(){
        logger.info("createNewClient task started");
        Client client = Instancio.of(Client.class)
                .set(Select.field(Client::getAgency), "Agency " + (int) (Math.random() * 6))
                .set(Select.field(Client::getStatus), "ACTIVE") // Фиксированный статус
                .generate(Select.field(Client::getDob), gen -> gen.temporal().localDate().range(LocalDate.now().minusYears(70),LocalDate.now().minusYears(5)))
                .generate(Select.field(Client::getCreatedAt), gen -> gen.temporal().localDateTime().range(LocalDateTime.now().minusYears(21),LocalDateTime.now()))
                .create();
        clientRepository.add(client);
        logger.info("client {} was added",client.getGuid());
    }

    private void createNewNote(){
        logger.info("createNewNote task started");
        Client client = null;
        try {
            client = getRandomClient();

        }
        catch (IllegalArgumentException e){
            return;
        }
        Note note = Instancio.of(Note.class)
                .generate(Select.field(Note::getComments),gen -> gen.text().loremIpsum() )
                .generate(Select.field(Note::getModifiedAt), gen -> gen.temporal().localDateTime().range(LocalDateTime.now().minusYears(19),LocalDateTime.now()))
                .set(Select.field(Note::getClientGuid), UUID.randomUUID())
                .generate(Select.field(Note::getDatetime), gen -> gen.temporal().localDateTime().past())
                .set(Select.field(Note::getLoggedUser), "User" + (int) (Math.random() * 100))
                .generate(Select.field(Note::getCreatedAt), gen -> gen.temporal().localDateTime().range(LocalDateTime.now().minusYears(20),LocalDateTime.now().minusYears(5)))
                .create();
        note.setClientGuid(client.getGuid());
        notesRepository.addNote(note);
        logger.info("note {} for client {} was added",note,client.getGuid());

    }

    private Client getRandomClient(){
        int amount = clientRepository.getClientAmount();
        if(amount<1){
            logger.info("Have no clients for adding or updating notes");
            throw new IllegalArgumentException();
        }
        int randomClientNumber = random.nextInt(amount);
        return clientRepository.getClientByIndex(randomClientNumber);
    }
}
