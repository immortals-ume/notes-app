package com.immortals.notesapp.service;

import com.immortals.notesapp.model.dto.NoteDto;
import com.immortals.notesapp.model.entity.Note;

import java.util.List;

public interface NotesService {
    Note addNotes(NoteDto noteDto);

    List<Note> getNotes();

    Note getNote(Long id);

    Note updateNote(Long noteId, Note note);

    String deleteNotes(List<Long> noteIds);
}
