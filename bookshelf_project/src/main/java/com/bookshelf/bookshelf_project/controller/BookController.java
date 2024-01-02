package com.bookshelf.bookshelf_project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bookshelf.bookshelf_project.entity.Book;
import com.bookshelf.bookshelf_project.entity.Item;
import com.bookshelf.bookshelf_project.entity.Store;
import com.bookshelf.bookshelf_project.repository.BookRepository;
import com.bookshelf.bookshelf_project.repository.ItemRepository;
import com.bookshelf.bookshelf_project.repository.StoreRepository;
@Controller
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private StoreRepository storeRepository;

    @GetMapping("/books")
    public ModelAndView getAllBooks(){
        ModelAndView mav= new ModelAndView("list-books");
        mav.addObject("books",  bookRepository.findAll());
        return mav;
    }

    @GetMapping("/addBookForm")
    public ModelAndView addBookForm(){
        ModelAndView mav = new ModelAndView("add-book-form");
        Book book = new Book();
        mav.addObject("book", book);
        return mav;
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute Book book){
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/showUpdateBookForm")
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
    public String deleteBook(@RequestParam Long bookId){
        bookRepository.deleteById(bookId);
        return "redirect:/books";
    }

    @GetMapping("/addPrice")
    public ModelAndView addPriceForm(@RequestParam Long bookId){
        ModelAndView mav= new ModelAndView("add-price-form");
        Optional<Book> optionalBook= bookRepository.findById(bookId);
        Book book = new Book();
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
        }
        mav.addObject("book", book);
        List<Store>stores=storeRepository.findAll();
        mav.addObject("stores", stores);
        mav.addObject("stores", stores);
        Item newItem = new Item();
        mav.addObject("item", newItem);
        return mav;
    }

    @PostMapping("/savePrice")
    public String savePrice(@ModelAttribute Item item, @RequestParam Long bookId) {
        item.setBook(bookRepository.getReferenceById(bookId));
        itemRepository.save(item);
        return "redirect:/books";
    }

}
