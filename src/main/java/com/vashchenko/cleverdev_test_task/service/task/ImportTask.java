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
public class ImportTask {
    private final NotesDataFetcher notesFetcher;
    private final UserService userService;
    private final NoteService noteService;
    private final PatientService patientService;

    private final NoteMapper noteMapper;


    //Statistics block
    private final ImportStatist importStatist;
    @Async
    public void execute(ClientInfoResponseDto client ){
        long start = System.currentTimeMillis();
        log.debug("Task {} start import",client.guid());
        List<NoteInfoResponseDto> notes = notesFetcher.getNotesByClient(client.agency(),client.guid());
        importStatist.incrementAllNotesCounter(notes.size());
        Patient patient = patientService.findByOldGuid(client.guid());
        if(patient!=null&&isActivePatient(patient.getStatusId())){
            for (NoteInfoResponseDto note : notes) {
                try {
                    importNote(note,patient,client);
                }
                catch (Exception e){
                    log.error(String.format("Exception saving note with guid %s ",note.guid()),e);
                }

            }
        }
        else {
            log.info(String.format("Patient with guid %s was not found in New System",client.guid()));
            importStatist.incrementNotFoundClientsCounter();
            importStatist.incrementSkippedNotesCounter(notes.size());
        }
        log.debug("Task %s end import with time {}",System.currentTimeMillis() - start);
    }
    private void importNote(NoteInfoResponseDto noteDto, Patient patient,ClientInfoResponseDto clientDto) {
        User user = findOrCreateUser(noteDto.loggedUser());
        Note note = noteService.findNoteByPatientGuidAndUserLoginAndCreatedDateTime(clientDto.guid(),user.getLogin(),noteDto.createdDateTime());
        updateOrCreateNote(note,noteDto,user,patient);
    }

    private void updateOrCreateNote(Note note, NoteInfoResponseDto noteDto, User user, Patient patient) {
        if(note==null){
            processNotExistingNote(noteDto,user,patient);
        }
        else {
            processExistingNote(note,noteDto);
        }
    }

    private void processExistingNote(Note note,NoteInfoResponseDto noteDto){
        log.debug(String.format("Note with guid '%s' was found in New System",noteDto.guid()));
        if(note.getLastModifiedAt().isBefore(noteDto.modifiedDateTime())){
            note.setNote(noteDto.noteBody());
            log.debug(String.format("There is no way to determine the last person who edited '%s' document. The default value is null.",noteDto.guid()));
            note.setLastModifier(null);
            note.setLastModifiedAt(noteDto.modifiedDateTime());
            noteService.save(note);
            log.info(String.format("Note with guid '%s' was successful updated in New System by Id '%s'",noteDto.guid(),note.getId()));
            importStatist.incrementSavedNotesCounter();
        }
        else {
            log.debug(String.format("Note with guid '%s' a later modification date as note with id '%s",noteDto.guid(),note.getId()));
            importStatist.incrementSkippedNotesCounter();
        }
    }

    private void processNotExistingNote(NoteInfoResponseDto noteDto, User user,
                                        Patient patient){
        log.debug(String.format("Note with guid '%s' was not found in New System",noteDto.guid()));
        Note noteToSave = noteMapper.toEntity(noteDto);
        noteToSave.setCreator(user);
        noteToSave.setPatient(patient);
        if(noteToSave.getCreatedAt().isBefore(noteToSave.getLastModifiedAt())){
            log.debug(String.format("There is no way to determine the last person who edited '%s' document. The default value is null.",noteToSave.getId()));
            noteToSave.setLastModifier(null);
        }
        else {
            noteToSave.setLastModifier(user);
        }
        noteService.save(noteToSave);
        log.info(String.format("Note with guid '%s' was successful saved to New System by Id '%s'",noteDto.guid(),noteToSave.getId()));
        importStatist.incrementSavedNotesCounter();
    }

    private boolean isActivePatient(short statusId) {
        return statusId==200||statusId==210||statusId==230;
    }

    private User findOrCreateUser(String login) {
        User user = userService.findByLogin(login);
        if(user==null){
            User newUser = new User();
            newUser.setLogin(login);
            user = userService.save(newUser);
            log.info(String.format("Was created user with login '%s' and id '%s'",login,user.getId()));
            importStatist.incrementCreatedUsersCounter();
        }
        return user;
    }
}
