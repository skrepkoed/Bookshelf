package com.bookshelf.bookshelf_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookshelf.bookshelf_project.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role  findByName(String name);
} 
