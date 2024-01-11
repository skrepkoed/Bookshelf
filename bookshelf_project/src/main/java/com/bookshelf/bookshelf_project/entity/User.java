package com.bookshelf.bookshelf_project.entity;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
import jakarta.persistence.OneToMany;
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
    @Column
    Integer speedOfReading=100;
    @Column(nullable = true)
    LocalDate deadLine=LocalDate.now();
    @ManyToMany(fetch = FetchType.EAGER, cascade =  {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
        name = "users_roles",
        joinColumns = {@JoinColumn(name="user_id",referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name="role_id",referencedColumnName = "id")}
    )
    private Set<Role> roles=new HashSet<>();
    
    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Book> books;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.ALL})
    private Set<LogAction> logActions;
    

    public String rolesAsString(){
        return getRoles().toString();
    }
}
