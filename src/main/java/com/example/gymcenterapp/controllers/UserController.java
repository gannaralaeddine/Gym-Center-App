package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.repositories.UserRepository;
import com.example.gymcenterapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:35729")
public class UserController
{
    @Autowired
    UserService userService;


    @GetMapping("/retrieve-all-users")
    @ResponseBody
    public List<User> getAllUser() {
        return userService.retrieveAllUsers();
    }


    @GetMapping("/retrieve-user/{user-id}")
    @ResponseBody
    public User retrieveUser(@PathVariable("user-id") Long userId) {
        return userService.retrieveUser(userId);
    }


    @PostMapping(value = "/add-user")
    @ResponseBody
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
