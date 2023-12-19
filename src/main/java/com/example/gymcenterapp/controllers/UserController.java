package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Role;
import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.repositories.UserRepository;
import com.example.gymcenterapp.services.RoleServiceImpl;
import com.example.gymcenterapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController
{
    @Autowired
    UserService userService;

    @Autowired
    RoleServiceImpl roleService;

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


    @PostMapping(value = "/register-user")
    @ResponseBody
    public User addUser(@RequestBody User user)
    {
        return userService.addUser(user);
    }



    @PostMapping(value = "/add-role")
    @ResponseBody
    public Role addURole(@RequestBody Role role) {
        return roleService.addRole(role);
    }
}
