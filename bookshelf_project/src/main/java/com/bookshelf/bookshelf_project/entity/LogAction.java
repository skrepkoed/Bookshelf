package com.bookshelf.bookshelf_project.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "logactions")
public class LogAction {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    //@JoinColumn(name = "user_id",referencedColumnName="id", nullable = true)
    private User user;
}
