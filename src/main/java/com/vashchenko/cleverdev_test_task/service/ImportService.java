package com.vashchenko.cleverdev_test_task.service;

import com.vashchenko.cleverdev_test_task.fetchers.ClientDataFetcher;
import com.vashchenko.cleverdev_test_task.fetchers.dto.response.ClientInfoResponseDto;
import com.vashchenko.cleverdev_test_task.service.task.ImportTask;
import com.vashchenko.cleverdev_test_task.statistics.ImportStatist;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
@AllArgsConstructor
public class ImportService {
    private final ClientDataFetcher clientFetcher;
    private final ImportStatist statist;
    private final ImportTask task;

    public void runImport() {
        log.debug("Start import");
        List<ClientInfoResponseDto> clients = clientFetcher.getAllClients();
        statist.setAllClientsCounter(clients.size());
        for (ClientInfoResponseDto client : clients) {
            task.execute(client);
        }
        log.debug("Import was completed");
    }


}
