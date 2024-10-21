package com.vashchenko.cleverdev_test_task.fetchers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@AllArgsConstructor
public class FetchersConfig {
    private final RestTemplateBuilder templateBuilder;

    @Bean
    RestTemplate usersRestTemplate(@Value("${url.old}")String oldUrl){
        return templateBuilder.uriTemplateHandler(new DefaultUriBuilderFactory(oldUrl)).build();
    }

}
