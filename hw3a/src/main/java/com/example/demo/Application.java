package com.example.demo;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Application {
    static long id = 1L;
    //private static RoleRepository roleRepository;
    public static void main(String[] args) {
        UserRepository userRepository = SpringApplication.run(Application.class, args).getBean(UserRepository.class);

        saveUser(userRepository, "admin");
        saveUser(userRepository, "reader");
        saveUser(userRepository, "lera");


    }
    private static void saveRoles(RoleRepository repository, String name) {
        Role role = new Role();
        role.setId(id++);
        role.setRole(name);

        repository.save(role);
    }
    private static void saveUser(UserRepository repository, String name) {
        User user = new User();
        user.setId(id++);
        user.setName(name);
        user.setPassword(name);

        repository.save(user);
    }
}
