package com.vashchenko.cleverdev_test_task.service.task;

import com.vashchenko.cleverdev_test_task.entity.Note;
import com.vashchenko.cleverdev_test_task.entity.Patient;
import com.vashchenko.cleverdev_test_task.entity.User;
import com.vashchenko.cleverdev_test_task.fetchers.NotesDataFetcher;
import com.vashchenko.cleverdev_test_task.fetchers.dto.response.ClientInfoResponseDto;
import com.vashchenko.cleverdev_test_task.fetchers.dto.response.NoteInfoResponseDto;
import com.vashchenko.cleverdev_test_task.mappers.NoteMapper;
import com.vashchenko.cleverdev_test_task.service.NoteService;
import com.vashchenko.cleverdev_test_task.service.PatientService;
import com.vashchenko.cleverdev_test_task.service.UserService;
import com.vashchenko.cleverdev_test_task.statistics.ImportStatist;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class ImportTask{
    private final NotesDataFetcher notesFetcher;
    private final UserService userService;
    private final NoteService noteService;
    private final PatientService patientService;

    private final NoteMapper noteMapper;


    //Statistics block
    private final ImportStatist importStatist;
    public void execute(ClientInfoResponseDto client ){
        long start = System.currentTimeMillis();
        log.info("Task '{}' start import",client.guid());
        List<NoteInfoResponseDto> notes = notesFetcher.getNotesByClient(client.agency(),client.guid());
        importStatist.incrementAllNotesCounter(notes.size());
        Patient patient = patientService.findByOldGuid(client.guid());
        if(patient!=null&& !isBlockedPatient(patient.getStatusId())){
            log.info("Patient guid:'{}' isSkipped:'{}'",client.guid(),false);
            log.debug(notes +" for "+client);
            for (NoteInfoResponseDto note : notes) {
                try {
                    importNote(note,patient,client);
                }
                catch (Exception e){
                    log.error("Exception saving note with guid '{}' ",note.guid(),e);
                }

            }
        }
        else {
            log.info("Patient guid:'{}' isSkipped:'{}'",client.guid(),true);
            importStatist.incrementSkippedClientsCounter();
            importStatist.incrementSkippedNotesCounter(notes.size());
        }
        log.info("Task '{}' end import with time '{}' ms",client.guid(),System.currentTimeMillis() - start);
    }
    void importNote(NoteInfoResponseDto noteDto, Patient patient,ClientInfoResponseDto clientDto) {
        User user = findOrCreateUser(noteDto.loggedUser());
        Note note = noteService.findNoteByPatientGuidAndUserLoginAndCreatedDateTime(clientDto.guid(),user.getLogin(),noteDto.createdDateTime());
        updateOrCreateNote(note,noteDto,user,patient);
    }

    void updateOrCreateNote(Note note, NoteInfoResponseDto noteDto, User user, Patient patient) {
        if(note==null){
            processNotExistingNote(noteDto,user,patient);
        }
        else {
            processExistingNote(note,noteDto);
        }
    }

    void processExistingNote(Note note,NoteInfoResponseDto noteDto){
        log.info("Note with guid '{}' was found in New System",noteDto.guid());
        if(note.getLastModifiedAt().isBefore(noteDto.modifiedDateTime())){
            note.setNote(noteDto.noteBody());
            log.info("There is no way to determine the last person who edited '{}' document. The default value is null.",noteDto.guid());
            note.setLastModifier(null);
            note.setLastModifiedAt(noteDto.modifiedDateTime());
            noteService.save(note);
            log.info("Note with guid '{}' was successful updated in New System by Id '{}'",noteDto.guid(),note.getId());
            importStatist.incrementSavedNotesCounter();
        }
        else {
            log.info("Note with guid '{}' has newest version as note with id '{}",noteDto.guid(),note.getId());
            importStatist.incrementSkippedNotesCounter();
        }
    }

    void processNotExistingNote(NoteInfoResponseDto noteDto, User user,
                                        Patient patient){
        log.info("Note with guid '{}' was not found in New System",noteDto.guid());
        Note noteToSave = noteMapper.toEntity(noteDto);
        noteToSave.setCreator(user);
        noteToSave.setPatient(patient);
        if(noteToSave.getCreatedAt().isBefore(noteToSave.getLastModifiedAt())){
            log.info("There is no way to determine the last person who edited '{}' document. The default value is null.",noteToSave.getId());
            noteToSave.setLastModifier(null);
        }
        else {
            noteToSave.setLastModifier(user);
        }
        noteService.save(noteToSave);
        log.info("Note with guid '{}' was successful saved to New System by Id '{}'",noteDto.guid(),noteToSave.getId());
        importStatist.incrementSavedNotesCounter();
    }

    boolean isBlockedPatient(short statusId) {
        return statusId==200||statusId==210||statusId==230;
    }

    User findOrCreateUser(String login) {
        User user = userService.findByLogin(login);
        if(user==null){
            User newUser = new User();
            newUser.setLogin(login);
            user = userService.save(newUser);
            log.info("Was created user with login {}' and id '{}'",login,user.getId());
            importStatist.incrementCreatedUsersCounter();
        }
        return user;
    }
}
