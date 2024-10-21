package com.vashchenko.cleverdev_test_task.fetchers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ClientInfoResponseDto(String agency,
                                    UUID guid,
                                    String firstName,
                                    String lastName,
                                    String status,
                                    LocalDate dob,
                                    @JsonProperty("createdDateTime")
                                  LocalDateTime createdAt
){}
