package com.immortals.notesapp.repository;

import com.immortals.notesapp.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepository extends JpaRepository<Note,Long> {

    Note findByActiveIndIsTrueAndUniqueNotesId(String uniqueNotesId);

    List<Note> findAllByActiveIndIsTrueAndUserId(Long userId);
}
