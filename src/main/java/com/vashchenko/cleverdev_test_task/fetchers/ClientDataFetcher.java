package com.vashchenko.cleverdev_test_task.fetchers;

import com.vashchenko.cleverdev_test_task.fetchers.dto.response.ClientInfoResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Log4j2
public class ClientDataFetcher {

    private final RestTemplate restTemplate;
    private final String usersUrl;

    ClientDataFetcher(RestTemplate restTemplate,
                      @Value("${url.old.clients}")String url) {
        this.restTemplate = restTemplate;
        this.usersUrl =url;
    }

    public List<ClientInfoResponseDto> getAllClients(){
        try {
            ClientInfoResponseDto[] clients = restTemplate.postForObject(usersUrl,null, ClientInfoResponseDto[].class);
            if(clients==null) clients = new ClientInfoResponseDto[0];
            log.info(String.format("%s clients were fetched from Old System",clients.length));
            log.debug(Arrays.toString(clients));
            return Arrays.asList(clients);
        }
        catch (RestClientException e){
            log.error(e,e.fillInStackTrace());
            return new ArrayList<ClientInfoResponseDto>();
        }
    }

}
