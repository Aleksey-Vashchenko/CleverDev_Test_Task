package com.vashchenko.cleverdev_test_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CleverDevTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleverDevTestTaskApplication.class, args);
    }

}
