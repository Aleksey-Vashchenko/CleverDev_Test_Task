package com.vashchenko.cleverdev_test_task.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@AllArgsConstructor
public class Config {


    @Bean
    public ExecutorService executorService(@Value("${executor.threads}") Integer threadAmounts){
        return Executors.newFixedThreadPool(threadAmounts);
    }
}
