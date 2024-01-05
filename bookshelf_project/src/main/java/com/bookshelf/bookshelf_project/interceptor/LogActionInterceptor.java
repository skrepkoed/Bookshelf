package com.bookshelf.bookshelf_project.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.bookshelf.bookshelf_project.service.LogActionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class LogActionInterceptor implements HandlerInterceptor {
    @Autowired
    private LogActionService logActionService;
    
    @Override
    public void afterCompletion(HttpServletRequest request, 
    HttpServletResponse response,
    Object handler, @Nullable Exception ex)  {
         logActionService.createLog(request);
    }
}
