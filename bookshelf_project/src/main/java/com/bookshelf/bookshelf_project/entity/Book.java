package com.bookshelf.bookshelf_project.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany(mappedBy = "userBooks",fetch = FetchType.LAZY)
    private List<User> users=new ArrayList<>();


    public void addUser(User user){
        getUsers().add(user);
    }
    
}
