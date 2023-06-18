package com.immortals.notesapp.controller;

import com.immortals.notesapp.model.dto.NoteDto;
import com.immortals.notesapp.model.entity.Note;
import com.immortals.notesapp.service.NotesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notes")
public class NotesController {

    private final NotesService notesService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Note addNotes(@Valid @RequestBody NoteDto noteDto) {
        return notesService.addNotes(noteDto);
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getListNote() {
        return notesService.getNotes();
    }

    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Note getNotesById(@PathVariable Long id) {
        return notesService.getNote(id);
    }

    @PatchMapping(value = "/{noteId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Note updateNote(@PathVariable Long noteId, @RequestBody Note note) {
        return notesService.updateNote(noteId, note);
    }

    @DeleteMapping(value = "/{noteIds}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public String deleteNotes(@PathVariable List<Long> noteIds) {

        return notesService.deleteNotes(noteIds);
    }
}
