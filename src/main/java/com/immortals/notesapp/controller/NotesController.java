package com.immortals.notesapp.controller;

import com.immortals.notesapp.model.dto.NoteDto;
import com.immortals.notesapp.model.entity.Note;
import com.immortals.notesapp.service.NotesService;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notes")
public class NotesController {

    private final NotesService notesService;


    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Timed
    @Transactional(timeout = 120)
    public Note addNotes(@Valid @RequestBody NoteDto noteDto) {
        return notesService.addNotes(noteDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Timed
    @Transactional(timeout = 120)
    public List<Note> getListNote(@Valid @NotNull @RequestParam("userId") Long userId) {
        return notesService.getNotes(userId);
    }

    @GetMapping(value = "/{uniqueNoteId}")
    @ResponseStatus(HttpStatus.OK)
    @Timed
    @Transactional(timeout = 120)
    public Note getNotesById(@Valid @NotNull @PathVariable String uniqueNoteId) {
        return notesService.getNote(uniqueNoteId);
    }

    @PatchMapping(value = "/{uniqueNoteId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Timed
    @Transactional(timeout = 120)
    public String updateNote(@Valid @NotNull @PathVariable String uniqueNoteId, @Valid @RequestBody NoteDto noteDto) {
        return notesService.updateNote(uniqueNoteId, noteDto);
    }

    @DeleteMapping(value = "/{uniqueNoteId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Timed
    @Transactional(timeout = 120)
    public String deleteNote(@Valid @NotNull @PathVariable String uniqueNoteId) {
        return notesService.deleteNote(uniqueNoteId);
    }
}
