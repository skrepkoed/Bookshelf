package com.bookshelf.bookshelf_project.security_policy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bookshelf.bookshelf_project.entity.Role;
import com.bookshelf.bookshelf_project.entity.User;
import com.bookshelf.bookshelf_project.repository.RoleRepository;
import com.bookshelf.bookshelf_project.repository.UserRepository;

import jakarta.transaction.Transactional;
@Component
public class SetupLoader implements
  ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private RoleRepository roleRepository;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup||userRepository.findByEmail("admin@admin.com")!=null)
            return;
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_READ_ONLY");
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setName("Admin");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("admin@admin.com");
        user.getRoles().add(adminRole);
        userRepository.save(user);

        alreadySetup = true;
    }
    @Transactional
    Role createRoleIfNotFound(String name) {
 
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
        return role;
    }
}
