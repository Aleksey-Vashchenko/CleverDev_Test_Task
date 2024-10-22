package com.vashchenko.cleverdev_test_task.statistics;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ImportStatist {
    private final AtomicInteger savedNotesCounter = new AtomicInteger();
    private final AtomicInteger savedUsersCounter = new AtomicInteger();
    private final AtomicInteger skippedClientsCounter = new AtomicInteger();
    private final AtomicInteger skippedNotesCounter = new AtomicInteger();
    private final AtomicInteger allClientsCounter = new AtomicInteger();
    private final AtomicInteger allNotesCounter = new AtomicInteger();

    public void incrementSavedNotesCounter(){
        savedNotesCounter.incrementAndGet();
    }
    public void incrementCreatedUsersCounter(){
        savedUsersCounter.incrementAndGet();
    }
    public void incrementSkippedClientsCounter(){
        skippedClientsCounter.incrementAndGet();
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

    @Override
    public String toString() {
        return "ImportStatist{" +
                "savedNotesCounter=" + savedNotesCounter +
                ", savedUsersCounter=" + savedUsersCounter +
                ", notFoundClientsCounter=" + skippedClientsCounter +
                ", skippedNotesCounter=" + skippedNotesCounter +
                ", allClientsCounter=" + allClientsCounter +
                ", allNotesCounter=" + allNotesCounter +
                '}';
    }

    public void resetValues(){
        savedNotesCounter.set(0);
        savedUsersCounter.set(0);
        skippedClientsCounter.set(0);
        skippedNotesCounter.set(0);
        allClientsCounter.set(0);
        allNotesCounter.set(0);
    }
}
