package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {
   // private final UserServiceImpl userService;


    //@Autowired
   // public AdminController(UserServiceImpl userService) {
   //     this.userService = userService;
   // }

    @GetMapping
    public String users() {
        return "admin";
    }

}