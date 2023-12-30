package com.bookshelf.bookshelf_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookshelf.bookshelf_project.entity.Store;

public interface StoreRepository extends JpaRepository<Store,Long> {

}
