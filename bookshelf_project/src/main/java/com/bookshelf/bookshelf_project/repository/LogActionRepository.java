package com.bookshelf.bookshelf_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookshelf.bookshelf_project.entity.LogAction;
import com.bookshelf.bookshelf_project.entity.User;

import java.util.List;
import java.util.Set;


public interface LogActionRepository extends JpaRepository<LogAction,Long> {
    Set<LogAction> findByUser(User user);
}
