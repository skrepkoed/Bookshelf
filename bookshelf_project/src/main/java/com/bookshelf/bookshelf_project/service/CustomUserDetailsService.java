package com.bookshelf.bookshelf_project.service;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookshelf.bookshelf_project.entity.User;
import com.bookshelf.bookshelf_project.repository.UserRepository;
import com.bookshelf.bookshelf_project.security_policy.CustomUserDetails;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository){this.userRepository = userRepository;}
    
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException{
        User user= userRepository.findByEmail(usernameOrEmail);
        if (user!=null) {
            return new CustomUserDetails(user.getId(),user.getEmail(),
            user.getPassword(),user.getRoles().stream().
            map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
        }else{
            throw new UsernameNotFoundException("Invalid Email or Password");
        }
    }
}
