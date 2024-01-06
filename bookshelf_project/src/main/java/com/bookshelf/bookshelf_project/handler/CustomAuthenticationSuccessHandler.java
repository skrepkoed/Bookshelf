package com.bookshelf.bookshelf_project.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.bookshelf.bookshelf_project.entity.Role;
import com.bookshelf.bookshelf_project.entity.User;
import com.bookshelf.bookshelf_project.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class CustomAuthenticationSuccessHandler implements 
AuthenticationSuccessHandler {
  
  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
  @Autowired
  private UserRepository userRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, 
    HttpServletResponse response, Authentication authentication)
    throws IOException {
    handle(request, response, authentication);
  }

  protected void handle(HttpServletRequest request,HttpServletResponse response, 
    Authentication authentication) throws IOException {
      User user= userRepository.findByEmail(authentication.getName());
      if (user.getRoles().stream().anyMatch(Role::isAdmin)) {
        redirectStrategy.sendRedirect(request, response, "admin/books");
      }else{
        redirectStrategy.sendRedirect(request, response, "/bookshelf/"+user.getId()+"/books");
      }
  }

}
