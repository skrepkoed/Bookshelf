package com.bookshelf.bookshelf_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookshelf.bookshelf_project.entity.Book;
import com.bookshelf.bookshelf_project.entity.Item;
import java.util.List;


public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByBook(Book book);
}
