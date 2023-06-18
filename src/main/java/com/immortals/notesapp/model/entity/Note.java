package com.immortals.notesapp.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name = "note", schema = "notes")
public class Note {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "note_id", nullable = false)
    private Long noteId;

    @Column(name = "title", nullable = false,unique = true)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "pined_ind")
    private Boolean pinedInd;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "active_ind")
    private Boolean activeInd;

}
