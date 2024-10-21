package com.vashchenko.cleverdev_test_task.fetchers.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public record ClientInfoRequestDto(String agency,
                                   LocalDate dateFrom,
                                   LocalDate dateTo,
                                   UUID clientGuid){
}
