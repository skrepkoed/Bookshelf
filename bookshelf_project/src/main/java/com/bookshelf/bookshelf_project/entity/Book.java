package com.bookshelf.bookshelf_project.entity;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
@Table(name = "BOOKS")
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title",nullable = false)
    String title;
    @Column(name = "title", nullable = true)
    String author;
    @Column(name = "author", nullable = true)
    String publisher;
    @Column(name = "year", nullable = true)
    int year;
    @Column(name = "genre", nullable = false)
    String genre;

}
