package com.vashchenko.cleverdev_test_task.mappers;

import com.vashchenko.cleverdev_test_task.entity.Note;
import com.vashchenko.cleverdev_test_task.fetchers.dto.response.NoteInfoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    @Mappings({
            @Mapping(source = "modifiedDateTime", target = "lastModifiedAt"),
            @Mapping(source = "createdDateTime", target = "createdAt"),
            @Mapping(source = "noteBody", target = "note")
    })
    Note toEntity(NoteInfoResponseDto userDTO);
}
