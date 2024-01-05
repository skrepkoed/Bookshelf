package com.bookshelf.bookshelf_project.service;

import org.springframework.stereotype.Service;

import com.bookshelf.bookshelf_project.entity.LogAction;
import com.bookshelf.bookshelf_project.entity.User;
import com.bookshelf.bookshelf_project.repository.LogActionRepository;
import com.bookshelf.bookshelf_project.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class LogActionService {
    private final UserRepository userRepository;
    private final LogActionRepository logActionRepository;

    public LogActionService(UserRepository userRepository,LogActionRepository logActionRepository   ){
        this.userRepository=userRepository;
        this.logActionRepository=logActionRepository;
    }

    public void createLog(HttpServletRequest request){
        LogAction logAction = new LogAction();
        String userName="Anonymus user";
        if (request.getUserPrincipal()!=null) {
            User user=userRepository.findByEmail(request.getUserPrincipal().getName());
            logAction.setUser(user);
            userName=user.getEmail();
        }
        logAction.setDesription(userName + " made " + request.getMethod()+" request on"
        +request.getRequestURI() + " URI" );      
        logActionRepository.save(logAction);
    }
}
