package com.bookshelf.bookshelf_project.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "books")
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String publisher;
    @Column
    private String genre;
    @Column
    private int originYear;
    @Column
    private int pages;
    @Column
    private boolean finished=false;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "book")
    private List<Item> items=new ArrayList<>();

    public int wordInBook(int avWordOnPage){
        return getPages()*avWordOnPage;
    }
    
}
