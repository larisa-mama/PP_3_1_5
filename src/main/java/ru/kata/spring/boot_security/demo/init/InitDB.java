package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InitDB {
    //@Autowired
    private  UserServiceImpl userService;

    public InitDB(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostConstruct
    //когда метод отмечен этой аннотацией, он будет вызываться сразу после внедрения зависимости. Может иметь любое имя, но не должен иметь никаких параметров. Он должен быть void и не должен быть static.
    public void initDB() {
       Set<Role> roleAdmin = new HashSet<>();
        Set<Role> roleUser = new HashSet<>();
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        userService.addRole(role1);
        userService.addRole(role2);
        roleAdmin.add(role1);
        roleAdmin.add(role2);
        roleUser.add(role2);
        User user1 = new User("admin", "Roma Melnikov",  "admin@mail.ru", "admin", roleAdmin);
        User user2 = new User("user", "Lara Melnikova",  "user@mail.ru", "user", roleUser);
        userService.add(user1);
        userService.add(user2);
    }
}
