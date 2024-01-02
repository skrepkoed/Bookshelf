package com.bookshelf.bookshelf_project.service;

import java.util.List;
import com.bookshelf.bookshelf_project.dto.UserDto;
import com.bookshelf.bookshelf_project.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
}
