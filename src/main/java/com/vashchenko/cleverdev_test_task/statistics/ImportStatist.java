package com.vashchenko.cleverdev_test_task.statistics;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope("prototype")
public class ImportStatist {
    private final AtomicInteger savedNotesCounter = new AtomicInteger();
    private final AtomicInteger savedUsersCounter = new AtomicInteger();
    private final AtomicInteger notFoundClientsCounter = new AtomicInteger();
    private final AtomicInteger skippedNotesCounter = new AtomicInteger();
    private final AtomicInteger allClientsCounter = new AtomicInteger();
    private final AtomicInteger allNotesCounter = new AtomicInteger();

    public void incrementSavedNotesCounter(){
        savedNotesCounter.incrementAndGet();
    }
    public void incrementCreatedUsersCounter(){
        savedUsersCounter.incrementAndGet();
    }
    public void incrementNotFoundClientsCounter(){
        notFoundClientsCounter.incrementAndGet();
    }
    public void incrementSkippedNotesCounter(){
        skippedNotesCounter.incrementAndGet();
    }
    public void incrementSkippedNotesCounter(Integer amount){
        skippedNotesCounter.addAndGet(amount);
    }

    public void incrementAllNotesCounter(Integer amount){
        allNotesCounter.addAndGet(amount);
    }

    public void setAllClientsCounter(Integer amount){
        allClientsCounter.set(amount);
    }
}
