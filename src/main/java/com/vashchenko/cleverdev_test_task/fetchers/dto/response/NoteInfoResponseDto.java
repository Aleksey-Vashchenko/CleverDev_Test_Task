package com.vashchenko.cleverdev_test_task.fetchers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record NoteInfoResponseDto(@JsonProperty("comments") String noteBody,
                                  UUID guid,
                                  LocalDateTime modifiedDateTime,
                                  UUID clientGuid,
                                  LocalDate datetime,
                                  String loggedUser,
                                  LocalDateTime createdDateTime
) {}
