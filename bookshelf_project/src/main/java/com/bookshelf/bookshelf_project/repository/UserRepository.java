package com.bookshelf.bookshelf_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookshelf.bookshelf_project.entity.User;
import java.util.List;
import com.bookshelf.bookshelf_project.entity.Book;


public interface UserRepository extends JpaRepository<User,Long> {
    User  findByEmail(String email);
    //List<User> findByUserBooks(List<Book> userBooks);
}
