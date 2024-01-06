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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "users_roles",
        joinColumns = {@JoinColumn(name="user_id",referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name="role_id",referencedColumnName = "id")}
    )
    private List<Role> roles=new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
        name = "users_books",
        joinColumns = {@JoinColumn(name="user_id",referencedColumnName = "id")},
        inverseJoinColumns ={@JoinColumn(name = "book_id",referencedColumnName = "id")}
    )
    private List<Book> userBooks=new ArrayList<>();

    public String rolesAsString(){
        return getRoles().toString();
    }
}
