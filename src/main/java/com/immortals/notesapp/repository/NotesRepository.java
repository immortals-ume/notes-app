package com.immortals.notesapp.repository;

import com.immortals.notesapp.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Note,Long> {
}
