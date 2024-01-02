package com.bookshelf.bookshelf_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {
    Long id;
    @NotEmpty
    String firstName;
    
    @NotEmpty
    String lastName;
    
    @Email
    @NotEmpty(message = "email should not be empty")
    String email;
    @NotEmpty(message = "email should not be empty")
    String password;
}
