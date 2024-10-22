package com.vashchenko.cleverdev_test_task.fetchers;

import com.vashchenko.cleverdev_test_task.fetchers.dto.request.ClientInfoRequestDto;
import com.vashchenko.cleverdev_test_task.fetchers.dto.response.NoteInfoResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class NotesDataFetcher {
    private final RestTemplate restTemplate;
    private final String notesUrl;

    NotesDataFetcher(RestTemplate restTemplate,
                    @Value("${url.old.notes}")String url) {
        this.restTemplate = restTemplate;
        this.notesUrl =url;
    }

    public List<NoteInfoResponseDto> getNotesByClient(String agency, UUID guid){
        LocalDate dateFrom = LocalDate.now().minusYears(100);
        LocalDate dateTo = LocalDate.now();
        try {
            ClientInfoRequestDto requestDto = new ClientInfoRequestDto(agency,dateFrom,dateTo,guid);
            log.debug(requestDto);
            NoteInfoResponseDto[] notes = restTemplate.postForObject(notesUrl,requestDto,NoteInfoResponseDto[].class);
            if(notes==null) notes=new NoteInfoResponseDto[0];
            log.info("{} notes were fetched from Old System for client {}",notes.length,guid);
            return Arrays.asList(notes);
        }
        catch (RestClientException e){
            log.error(e,e.fillInStackTrace());
            return new ArrayList<NoteInfoResponseDto>();
        }
    }
}
