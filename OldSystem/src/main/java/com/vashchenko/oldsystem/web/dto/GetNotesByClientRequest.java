package com.vashchenko.oldsystem.web.dto;

import java.time.LocalDate;
import java.util.UUID;

public record GetNotesByClientRequest(String agency, LocalDate dateFrom,
                                      LocalDate dateTo, UUID clientGuid) {
}
