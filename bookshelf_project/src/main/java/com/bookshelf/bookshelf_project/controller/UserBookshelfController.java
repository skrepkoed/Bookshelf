package com.bookshelf.bookshelf_project.controller;

import java.security.Principal;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
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
        if (book.getId()==null) {
            currentUser.getUserBooks().add(book);
            userRepository.save(currentUser);
       }
        bookRepository.save(book);
        return "redirect:/bookshelf/"+currentUser.getId()+"/books";
    }

    @GetMapping("/updateBook")
    public ModelAndView showUpdateForm(@RequestParam Long bookId){
        ModelAndView mav = new ModelAndView("add-book-form");
        Optional<Book> optionalBook= bookRepository.findById(bookId);
        Book book = new Book();
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
        }
        mav.addObject("book", book);
        return mav;
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam Long bookId,@ModelAttribute("currentUser")User currentUser){
        currentUser.getUserBooks().remove(bookRepository.findById(bookId).get());
        userRepository.save(currentUser);
        return "redirect:/bookshelf/"+currentUser.getId()+"/books";
    }
}
