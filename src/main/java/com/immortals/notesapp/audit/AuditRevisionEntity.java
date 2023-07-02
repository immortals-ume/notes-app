package com.immortals.notesapp.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;


@Entity
@Table(name = "rev_info")
@RevisionEntity(AuditRevisionListener.class)
@AttributeOverride(name = "timestamp", column = @Column(name = "rev_timestamp"))
@AttributeOverride(name = "id", column = @Column(name = "rev_id"))
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AuditRevisionEntity extends DefaultRevisionEntity {

    @Column(name = "user_id")
    private String user;
}