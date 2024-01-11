package com.bookshelf.bookshelf_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookshelf.bookshelf_project.entity.Book;
import com.bookshelf.bookshelf_project.entity.Item;
import java.util.List;
import com.bookshelf.bookshelf_project.entity.Store;



public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByBook(Book book);
    void deleteByBook(Book book);
    void deleteByStore(Store store);
}
