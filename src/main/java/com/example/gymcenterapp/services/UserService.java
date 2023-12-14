package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.interfaces.IUserService;
import com.example.gymcenterapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService
{

    @Autowired
    UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User retrieveUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
