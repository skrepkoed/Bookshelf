package com.bookshelf.bookshelf_project.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bookshelf.bookshelf_project.entity.Book;
import com.bookshelf.bookshelf_project.repository.BookRepository;

@Controller
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public ModelAndView getAllBooks(){
        ModelAndView mav= new ModelAndView("list-books");
        mav.addObject("books",  bookRepository.findAll());
        return mav;
    }

    @GetMapping("/addBookForm")
    public ModelAndView addBookForm(){
        ModelAndView mav = new ModelAndView("add-Book-form");
        Book Book = new Book();
        mav.addObject("Book", Book);
        return mav;
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute Book Book){
        bookRepository.save(Book);
        return "redirect:/books";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long BookId){
        ModelAndView mav = new ModelAndView("add-Book-form");
        Optional<Book> optionalBook= bookRepository.findById(BookId);
        Book Book = new Book();
        if (optionalBook.isPresent()) {
            Book = optionalBook.get();
        }
        mav.addObject("Book", Book);
        return mav;
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam Long BookId){
        bookRepository.deleteById(BookId);
        return "redirect:/books";
    }


}
