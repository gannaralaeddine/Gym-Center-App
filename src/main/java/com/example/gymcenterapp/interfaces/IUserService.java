package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService
{
    User addUser(User user);

    List<User> retrieveAllUsers();

    User retrieveUser(Long id);

    int numberOfUsers();

    User updateProfilePicture(User user, MultipartFile[] file);
}
