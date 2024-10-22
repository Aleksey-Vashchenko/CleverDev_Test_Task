package com.vashchenko.cleverdev_test_task;

import com.vashchenko.cleverdev_test_task.service.ImportService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ScheduledImportTask {
    private final ImportService importService;

    @Scheduled(cron = "0 15 0/2 * * *")
    void startTask(){
        importService.runImport();
    }
}
