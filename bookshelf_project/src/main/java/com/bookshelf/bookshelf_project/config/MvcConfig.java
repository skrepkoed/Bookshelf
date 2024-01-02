package com.bookshelf.bookshelf_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
@Configuration
public class MvcConfig {
     public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("inedx");
    }
}
