package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService
{
    ResponseEntity<String> addUser(User user);

    List<User> retrieveAllUsers();

    User retrieveUser(Long id);

    int numberOfUsers();

    User updateUserData(String userEmail, User user);

    User updateProfilePicture(User user, MultipartFile[] file) throws IOException;

    void addImagesToProfile(Long userId, MultipartFile[] files);
}
