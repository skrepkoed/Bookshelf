package com.bookshelf.bookshelf_project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.bookshelf.bookshelf_project.repository.UserRepository;
import com.bookshelf.bookshelf_project.service.CustomUserDetailsService;
import com.bookshelf.bookshelf_project.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private CustomUserDetailsService userDetailsService;    
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http ) throws Exception{
        http.csrf(csrf -> csrf.disable());
        return http.authorizeHttpRequests((auth)->{
            auth.requestMatchers("/register/**").permitAll();
            auth.requestMatchers("/h2-console/**").permitAll();
            auth.requestMatchers("/index").permitAll();
            auth.requestMatchers("/users").hasRole("ADMIN");
            auth.requestMatchers("/books").hasRole("USER");
        }

        ).formLogin((form) -> form
        .loginPage("/login")
        .defaultSuccessUrl("/books")
        .permitAll()
    )
    .logout((logout) -> logout.permitAll())
        .build();
    }
}
