package com.bookshelf.bookshelf_project.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bookshelf.bookshelf_project.entity.Book;
import com.bookshelf.bookshelf_project.entity.User;
import com.bookshelf.bookshelf_project.repository.BookRepository;
import com.bookshelf.bookshelf_project.repository.ItemRepository;
import com.bookshelf.bookshelf_project.repository.UserRepository;
import com.bookshelf.bookshelf_project.service.CalculateReadingTimeService;

import jakarta.servlet.http.HttpServletRequest;
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
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CalculateReadingTimeService calculateReadingTimeService;

    @ModelAttribute("currentUser")
    public User currentUser(Principal principal){
        return userRepository.findByEmail(principal.getName());
    }

    @GetMapping("books")
    public ModelAndView getAllBooks(@ModelAttribute("currentUser")User currentUser){
        ModelAndView mav= new ModelAndView("list-books");
        mav.addObject("books",  bookRepository.findByUserId(currentUser.getId()));
        mav.addObject("time", calculateReadingTimeService.calculate(currentUser));
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
           book.setUser(currentUser);
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
    
    @PutMapping("/markFinished")
    public String markFinished(@RequestParam Long bookId, @ModelAttribute("currentUser")User currentUser){
        Book book= bookRepository.getReferenceById(bookId);
        book.setFinished(true);
        bookRepository.save(book);
        return "redirect:/bookshelf/"+currentUser.getId()+"/books";

    }
    @PutMapping("/markUnFinished")
    public String markUnFinished(@RequestParam Long bookId, @ModelAttribute("currentUser")User currentUser){
        Book book= bookRepository.getReferenceById(bookId);
        book.setFinished(false);
        bookRepository.save(book);
        return "redirect:/bookshelf/"+currentUser.getId()+"/books";

    }
    @Transactional
    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam Long bookId,@ModelAttribute("currentUser")User currentUser){
        itemRepository.deleteByBook(bookRepository.getReferenceById(bookId));
        bookRepository.deleteByUser(currentUser);
        return "redirect:/bookshelf/"+currentUser.getId()+"/books";
    }

    @PostMapping("/addSpeed")
    public String addSpeedOdReading( HttpServletRequest request,Principal principal){
        User currentUser=userRepository.findByEmail(principal.getName());
        if (!(request.getParameter("speed").isEmpty()||request.getParameter("myDate").isEmpty())) {
            currentUser.setSpeedOfReading(Integer.parseInt(request.getParameter("speed")));
            currentUser.setDeadLine(LocalDate.parse(request.getParameter("myDate")));
            userRepository.save(currentUser);
        }
        return "redirect:/bookshelf/"+currentUser.getId()+"/books";
    }
    @GetMapping("/viewPrices")
    public ModelAndView viewPrices(@RequestParam Long bookId, @ModelAttribute("currentUser")User currentUser){
        
        ModelAndView mav= new ModelAndView("view-prices");
        mav.addObject("currentUser", currentUser);
        mav.addObject("items",itemRepository.findByBook(bookRepository.getReferenceById(bookId)));
        return mav;
    }
}
