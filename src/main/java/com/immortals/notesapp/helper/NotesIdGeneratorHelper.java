package com.immortals.notesapp.helper;

import com.immortals.notesapp.model.dto.NoteDto;

public class NotesIdGeneratorHelper {

    private NotesIdGeneratorHelper() {
        throw new IllegalStateException("NotesIdGeneratorHelper class");
    }
    public static String notesIdGeneratorHelper(NoteDto note){
        return String.valueOf(note.hashCode());
    }
}
