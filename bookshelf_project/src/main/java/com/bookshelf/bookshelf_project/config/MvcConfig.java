package com.bookshelf.bookshelf_project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bookshelf.bookshelf_project.interceptor.LogActionInterceptor;
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private LogActionInterceptor logActionInterceptor;
    
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("inedx");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
     registry.addInterceptor(logActionInterceptor );
    }
}
