package com.bookshelf.bookshelf_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookshelf.bookshelf_project.entity.Book;

public interface BookRepository extends JpaRepository<Book,Long> {

}
