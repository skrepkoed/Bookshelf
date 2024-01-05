package com.bookshelf.bookshelf_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookshelf.bookshelf_project.entity.LogAction;

public interface LogActionRepository extends JpaRepository<LogAction,Long> {

}
