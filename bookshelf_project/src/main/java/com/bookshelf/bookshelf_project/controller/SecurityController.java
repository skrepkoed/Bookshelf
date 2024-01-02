package com.bookshelf.bookshelf_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bookshelf.bookshelf_project.dto.UserDto;
import com.bookshelf.bookshelf_project.entity.User;
import com.bookshelf.bookshelf_project.service.UserService;

import jakarta.validation.Valid;

@Controller
public class SecurityController {
    @Autowired 
    private UserService userService;

    public SecurityController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user=new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
    BindingResult result, Model model){
        User existingUser= userService.findUserByEmail(userDto.getEmail());
        if (existingUser!=null && existingUser.getEmail()!= null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
            "На этот электронный адрес уже зарегистрированна учетная запись");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }
        userService.saveUser(userDto);

        return "redirect:/login";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users=userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
