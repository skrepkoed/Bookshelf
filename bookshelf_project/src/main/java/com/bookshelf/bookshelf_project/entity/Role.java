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
@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users=new ArrayList<>();
    
    public boolean isAdmin(){
        if (name.equals("ROLE_ADMIN")) {
            return true;
        }
        return false;
    }

    public boolean isUser(){
        if (name.equals("ROLE_USER")) {
            return true;
        }
        return false;
    }
}
