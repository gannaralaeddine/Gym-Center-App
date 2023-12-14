package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.User;

import java.util.List;

public interface IUserService
{
    User addUser(User user);

    List<User> retrieveAllUsers();

    User retrieveUser(Long id);
}
