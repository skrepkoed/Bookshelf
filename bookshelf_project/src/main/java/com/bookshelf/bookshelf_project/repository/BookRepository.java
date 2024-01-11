package com.bookshelf.bookshelf_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookshelf.bookshelf_project.entity.Book;
import java.util.List;

import com.bookshelf.bookshelf_project.entity.User;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByFinishedAndUser(boolean finished, User user);
    List<Book> findByUserId(Long id);
    void deleteByUser(User user);
    
}
