package com.immortals.notesapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "note", schema = "notes_master")
@Audited
@AuditTable(value="note_aud",schema = "notes_audit")
public class Note implements Serializable {
    @Serial
    private static final long serialVersionUID = 49189912909142857L;

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "note_id", nullable = false)
    private Long noteId;

    @Column(name = "unique_notes_id", nullable = false, length = 100)
    private String uniqueNotesId;

    @Column(name = "title", nullable = false, unique = true,length = -1)
    private String title;

    @Column(name = "content",length = -1)
    private String content;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "pined_ind")
    private Boolean pinedInd;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "active_ind")
    private Boolean activeInd;

}
