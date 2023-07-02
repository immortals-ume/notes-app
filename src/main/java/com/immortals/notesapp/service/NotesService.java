package com.immortals.notesapp.service;

import com.immortals.notesapp.model.dto.NoteDto;
import com.immortals.notesapp.model.entity.Note;

import java.util.List;

public interface NotesService {
    Note addNotes(NoteDto noteDto);

    List<Note> getNotes(Long userId);

    Note getNote(String uniqueNoteId);

    String updateNote(String uniqueNoteId, NoteDto noteDto);

    String deleteNote(String uniqueNotesId);
}
