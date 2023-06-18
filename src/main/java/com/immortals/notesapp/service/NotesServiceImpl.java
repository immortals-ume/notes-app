package com.immortals.notesapp.service;


import com.immortals.notesapp.mapper.NoteMapper;
import com.immortals.notesapp.model.dto.NoteDto;
import com.immortals.notesapp.model.entity.Note;
import com.immortals.notesapp.repository.NotesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotesServiceImpl implements NotesService {


    private final NotesRepository notesRepository;

    private final NoteMapper noteMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    @Override
    public Note addNotes(NoteDto noteDto) {
        Note note = new Note();
        try {
            note = notesRepository.saveAndFlush(noteMapper.toNote(noteDto));
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return note;
    }

    @Override
    public List<Note> getNotes() {
        return null;
    }

    @Override
    public Note getNote(Long id) {
        return null;
    }

    @Override
    public Note updateNote(Long noteId, Note note) {
        return null;
    }

    @Override
    public String deleteNotes(List<Long> noteIds) {
        return null;
    }
}
