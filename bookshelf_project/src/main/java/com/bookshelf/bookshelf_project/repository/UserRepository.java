package com.bookshelf.bookshelf_project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bookshelf.bookshelf_project.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User  findByEmail(String email);
}
