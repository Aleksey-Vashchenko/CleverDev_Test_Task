package com.vashchenko.cleverdev_test_task;

import com.vashchenko.cleverdev_test_task.service.ImportService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class ScheduledImportTask {
    private final ImportService importService;

    @Scheduled
    protected void startTask(){
        importService.runImport();
    }
}
