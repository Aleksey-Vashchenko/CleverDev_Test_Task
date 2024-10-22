package com.vashchenko.cleverdev_test_task.service;

import com.vashchenko.cleverdev_test_task.exceptions.ClientProcessException;
import com.vashchenko.cleverdev_test_task.fetchers.ClientDataFetcher;
import com.vashchenko.cleverdev_test_task.fetchers.dto.response.ClientInfoResponseDto;
import com.vashchenko.cleverdev_test_task.service.task.ClientImportTask;
import com.vashchenko.cleverdev_test_task.statistics.ImportStatist;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Component
@Log4j2
@AllArgsConstructor
public class ImportService {
    private final ClientDataFetcher clientFetcher;
    private final ImportStatist statist;
    private final ClientImportTask task;
    private final ExecutorService executorService;

    public void runImport() {
        log.debug("Start import");
        statist.resetValues();
        List<ClientInfoResponseDto> clients = clientFetcher.getAllClients();
        statist.setAllClientsCounter(clients.size());
        List<Future<?>> futures = new LinkedList<>();
        for (ClientInfoResponseDto client : clients) {
            Future<?> future = executorService.submit(() -> {
                try {
                    String threadName = "Thread "+client.guid();
                    Thread.currentThread().setName(threadName);
                    task.execute(client);
                }
                catch (Exception e){
                    throw new ClientProcessException(client,e);
                }
            });
            futures.add(future);
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException |ClientProcessException e) {
                log.error(e);
            }
        }
        log.info("Import was completed. Info about import "+statist);
    }


}
