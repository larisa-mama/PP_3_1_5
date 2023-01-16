package ru.kata.spring.boot_security.demo.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class AdminRestController {
    private final UserServiceImpl userService;

    public AdminRestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/allRoles")
    public ResponseEntity <List<Role>> getRole() {
        return new ResponseEntity<>(userService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/myPrincipal")
    public ResponseEntity <User> getPrincipal (Principal principal) {
        return new ResponseEntity<>(userService.findByEmail(principal.getName()), HttpStatus.OK);
    }
    @GetMapping("/oneUser/{id}")
    public ResponseEntity<User> getOneUser (@PathVariable("id") Long id, Model model) {
       try {
           return new ResponseEntity<>( userService.getUser(id), HttpStatus.OK);
       } catch (Exception ex) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }
@PostMapping("/makeUser")
    public ResponseEntity<User> createRestUser (@RequestBody User user) {
        List<String> list1 = user.getRoles().stream().map(r->r.getRole()).collect(Collectors.toList());
        List<Role> list2 = userService.listByRole(list1);
        user.setRoles(Set.copyOf(list2));
        userService.add(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
}
@PatchMapping("/changeUser")
    public ResponseEntity <User> updateRestUser (@RequestBody User user) {
    List<String> list1 = user.getRoles().stream().map(r->r.getRole()).collect(Collectors.toList());
    List<Role> list2 = userService.listByRole(list1);
    user.setRoles(Set.copyOf(list2));
    userService.edit(user);
    return new ResponseEntity<>(user, HttpStatus.OK);
}
@DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
}

}

