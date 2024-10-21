package com.vashchenko.cleverdev_test_task.mappers;

import com.vashchenko.cleverdev_test_task.entity.Note;
import com.vashchenko.cleverdev_test_task.fetchers.dto.response.NoteInfoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class NoteMapper {


    @Mapping(source = "modifiedDateTime", target = "lastModifiedAt")
    @Mapping(source = "createdDateTime", target = "createdAt")
    @Mapping(source = "noteBody", target = "note")
    public abstract Note toEntity(NoteInfoResponseDto noteDto);
}
