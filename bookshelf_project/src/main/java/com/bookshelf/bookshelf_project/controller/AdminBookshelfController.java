package com.bookshelf.bookshelf_project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bookshelf.bookshelf_project.entity.Book;
import com.bookshelf.bookshelf_project.entity.Item;
import com.bookshelf.bookshelf_project.entity.Role;
import com.bookshelf.bookshelf_project.entity.Store;
import com.bookshelf.bookshelf_project.entity.User;
import com.bookshelf.bookshelf_project.repository.BookRepository;
import com.bookshelf.bookshelf_project.repository.ItemRepository;
import com.bookshelf.bookshelf_project.repository.RoleRepository;
import com.bookshelf.bookshelf_project.repository.StoreRepository;
import com.bookshelf.bookshelf_project.repository.UserRepository;
@Controller
@RequestMapping(path = "admin/", method=RequestMethod.GET)
public class AdminBookshelfController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/books")
    public ModelAndView getAllBooks(){
        ModelAndView mav= new ModelAndView("list-books-admin");
        mav.addObject("books",  bookRepository.findAll());
        return mav;
    }

    @GetMapping("/addBook")
    public ModelAndView addBookForm(){
        ModelAndView mav = new ModelAndView("add-book-form-admin");
        Book book = new Book();
        mav.addObject("book", book);
        return mav;
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute Book book){
        bookRepository.save(book);
        return "redirect:/admin/books";
    }

    @GetMapping("/updateBook")
    public ModelAndView showUpdateForm(@RequestParam Long bookId){
        ModelAndView mav = new ModelAndView("add-book-form-admin");
        Optional<Book> optionalBook= bookRepository.findById(bookId);
        Book book = new Book();
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
        }
        mav.addObject("book", book);
        return mav;
    }

    @DeleteMapping("/deleteBook")
    public String deleteBook(@RequestParam Long bookId){
        bookRepository.deleteById(bookId);
        return "redirect:/admin/books";
    }
    @GetMapping("/makeUser")
    public String makeUser(@RequestParam Long userId){
        Role role = roleRepository.findByName("ROLE_USER");
        User user=userRepository.getReferenceById(userId);
        user.setRoles(new ArrayList<Role>(List.of(role)));
        userRepository.save(user);
        return "redirect:/admin/users";
    }
    @GetMapping("/makeAdmin")
    public String makeAdmin(@RequestParam Long userId){
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        Role roleUser = roleRepository.findByName("ROLE_USER");
        User user=userRepository.getReferenceById(userId);
        user.setRoles(new ArrayList<Role>(List.of(roleAdmin,roleUser)));
        userRepository.save(user);
        return "redirect:/admin/users";
    }
    @GetMapping("/makeReadOnly")
    public String makeReadOnly(@RequestParam Long userId){
        Role role = roleRepository.findByName("ROLE_READ_ONLY");
        User user=userRepository.getReferenceById(userId);
        user.setRoles(new ArrayList<Role>(List.of(role)));
        userRepository.save(user);
        return "redirect:/admin/users";
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
        return "redirect:/admin/books";
    }

    @GetMapping("/users")
    public ModelAndView users(){
        ModelAndView mav= new ModelAndView("users");
        List<User>users=userRepository.findAll();
        mav.addObject("users",  users);
        return mav;
    }

}