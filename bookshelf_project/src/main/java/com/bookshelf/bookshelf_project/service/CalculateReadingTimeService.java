package com.bookshelf.bookshelf_project.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookshelf.bookshelf_project.entity.Book;
import com.bookshelf.bookshelf_project.entity.User;
import com.bookshelf.bookshelf_project.repository.BookRepository;

@Service
public class CalculateReadingTimeService {
    private static final int WORDS_ON_PAGE=521;
    @Autowired
    private  BookRepository bookRepository;

    public CalculateReadingTimeService(BookRepository bookRepository){
        this.bookRepository=bookRepository;
    }

    public  float  calculate(User user){
        
        List<Book> nonFinishedBooks= bookRepository.findByFinishedAndUser(false,user);
        int userSpeed=user.getSpeedOfReading();
        if (nonFinishedBooks.isEmpty()) {
            return 0;
        }else{
            int totalAmountOfReading= nonFinishedBooks.stream().map(book->book.wordInBook(WORDS_ON_PAGE)).reduce(Integer::sum).get();
            float totalAmountOfTimeInHours=totalAmountOfReading/(userSpeed*60);
            LocalDate today= LocalDate.now();
            LocalDate deadline=user.getDeadLine();
            long diff= ChronoUnit.DAYS.between(today, deadline);
            return diff<=0 ? 0 : totalAmountOfTimeInHours/diff;       
        }
    }
}
