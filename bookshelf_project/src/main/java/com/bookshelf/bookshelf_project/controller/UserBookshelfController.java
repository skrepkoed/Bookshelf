package com.bookshelf.bookshelf_project.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bookshelf.bookshelf_project.entity.Book;
import com.bookshelf.bookshelf_project.entity.User;
import com.bookshelf.bookshelf_project.repository.BookRepository;
import com.bookshelf.bookshelf_project.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping(path = "bookshelf/{userId}", method=RequestMethod.GET)
public class UserBookshelfController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute("currentUser")
    public User currentUser(Principal principal){
        return userRepository.findByEmail(principal.getName());
    }

    @GetMapping("books")
    public ModelAndView getAllBooks(@ModelAttribute("currentUser")User currentUser){
        ModelAndView mav= new ModelAndView("list-books");
        mav.addObject("books",  currentUser.getUserBooks());
        return mav;
    }

    @GetMapping("addBook")
    public ModelAndView addBookForm(@ModelAttribute("currentUser")User currentUser){
        ModelAndView mav = new ModelAndView("add-book-form");
        Book book = new Book();
        mav.addObject("currentUser",currentUser);
        mav.addObject("book", book);
        return mav;
    }
    @Transactional
    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute Book book,Principal principal ){
        User currentUser=userRepository.findByEmail(principal.getName());
        currentUser.getUserBooks().add(book);
        bookRepository.save(book);
        userRepository.save(currentUser);
        return "redirect:/bookshelf/"+currentUser.getId()+"/books";
    }
}
