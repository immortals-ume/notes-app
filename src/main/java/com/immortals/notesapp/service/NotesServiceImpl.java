package com.immortals.notesapp.service;


import com.immortals.notesapp.mapper.NoteMapper;
import com.immortals.notesapp.model.dto.NoteDto;
import com.immortals.notesapp.model.entity.Note;
import com.immortals.notesapp.repository.NotesRepository;
import com.immortals.notesapp.service.exception.EntityNotFoundException;
import com.immortals.notesapp.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.immortals.notesapp.constant.NoteConstant.SUCCESSFUL_DELETE_STATUS;
import static com.immortals.notesapp.helper.NotesIdGeneratorHelper.notesIdGeneratorHelper;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "notes")
public class NotesServiceImpl implements NotesService {


    private final NotesRepository notesRepository;

    private final NoteMapper noteMapper;

    /**
     * creates new note resource using information captured using noteDto
     * once the DB record is created then it is saved to cache for Easier access by the user with UserId as key
     *
     * @param noteDto object to capture details for the specified notes
     * @return saved notes object with a unique notes id attached to it
     */
    @Cacheable(value = "note", key = "#noteDto.hashCode()")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    @Override
    public Note addNotes(NoteDto noteDto) {
        Note note = noteMapper.toNote(noteDto);
        note.setCreatedOn(DateUtils.getLocalDateTime());
        note.setUniqueNotesId(notesIdGeneratorHelper(noteDto));
        notesRepository.saveAndFlush(note);
        log.info("New Notes resource is created with unique notesID {}", note.getUniqueNotesId());
        return note;
    }

    /**
     * find the list of Notes for the particular user using userId passed  in uri
     *
     * @return list of notes for particular user (pageable)
     */
    @Override
    public List<Note> getNotes(Long userId) {
        return notesRepository.findAllByActiveIndIsTrueAndUserId(userId);
    }

    /**
     * find the details for Individual Notes (can be fetched from cache if not present will be fetched from DB)
     *
     * @param uniqueNoteId Unique hash assigned to the note when created
     * @return Notes object with oll the specified details
     */
    @Cacheable(value = "note", key = "#uniqueNoteId")
    @Override
    public Note getNote(String uniqueNoteId) {
        return findNote(uniqueNoteId);
    }

    private Note findNote(String uniqueNoteId) {
        return Optional.ofNullable(notesRepository.findByActiveIndIsTrueAndUniqueNotesId(uniqueNoteId))
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));
    }

    /**
     * Update the Existing note Entity in cache and Database
     *
     * @param uniqueNoteId Unique hash assigned to the note when created
     * @param noteDto      object to capture details for the specified notes
     * @return Unique hash assigned to the note when created if updated successfully
     */
    @CachePut(value = "note", key = "#uniqueNoteId")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    @Override
    public String updateNote(String uniqueNoteId, NoteDto noteDto) {

        Note note = findNote(uniqueNoteId);
        noteMapper.updateNoteFromDto(noteDto, note);
        notesRepository.saveAndFlush(note);
        log.info("Updated Existing Note Record with unique notesID {}", note.getUniqueNotesId());
        return uniqueNoteId;
    }

    @CacheEvict(value = "note", key = "#uniqueNoteId")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    @Override
    public String deleteNote(String uniqueNoteId) {

        Note note = findNote(uniqueNoteId);
        note.setActiveInd(Boolean.FALSE);
        notesRepository.saveAndFlush(note);
        log.info("Successfully Deleted Note Record with unique notesID {}", note.getUniqueNotesId());
        return SUCCESSFUL_DELETE_STATUS;
    }
}
