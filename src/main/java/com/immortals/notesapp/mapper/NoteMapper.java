package com.immortals.notesapp.mapper;

import com.immortals.notesapp.model.dto.NoteDto;
import com.immortals.notesapp.model.entity.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    @Mapping(target = "noteId", ignore = true)
    @Mapping(target= "createdBy",source = "userId")
    @Mapping(target = "activeInd",expression = "java(Boolean.TRUE)")
    @Mapping(target = "pinedInd",expression = "java(Boolean.FALSE)")
    Note toNote(NoteDto noteDto);

    void updateNoteFromDto(NoteDto noteDto, @MappingTarget Note note);
}
