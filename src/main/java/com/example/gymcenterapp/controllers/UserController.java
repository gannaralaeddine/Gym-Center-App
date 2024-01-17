package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Role;
import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.services.RoleServiceImpl;
import com.example.gymcenterapp.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/user")
public class UserController
{

    UserService userService;

    RoleServiceImpl roleService;

    @GetMapping("/retrieve-all-users")
    @ResponseBody
//    @RolesAllowed({ "ROLE_ADMIN" })
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


    @GetMapping(value = "/number-of-users")
    @ResponseBody
    public int numberOfUsers() {
        return userService.numberOfUsers();
    }


    @GetMapping("/retrieve-user-by-email/{email}")
    @ResponseBody
    public User retrieveUserByEmail(@PathVariable("email") String email) {
        User userDetails = userService.retrieveUserByEmail(email);

        System.out.println(userDetails);
        return userDetails;
    }

// Update User Data
//----------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = { "/update-user" })
    public User updateUserData(@RequestBody User user)
    {
        return userService.updateUserData(user.getUserEmail(), user);
    }


// Manage roles
    @PostMapping(value = "/add-role")
    @ResponseBody
    public Role addURole(@RequestBody Role role) {
        return roleService.addRole(role);
    }

    @GetMapping("/retrieve-all-roles")
    @ResponseBody
//    @RolesAllowed({ "ROLE_ADMIN" })
    public List<Role> getAllRoles() {
        return roleService.retrieveAllRoles();
    }


// Update User Profile Picture
//----------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = { "/update-profile-picture" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public User updateProfilePicture(@RequestPart("user") User user,
                                   @RequestPart("imageFile") MultipartFile[] images)
    {
        System.out.println("files length: " + images.length);
        return userService.updateProfilePicture(user, images);
    }


// Add Images to User profile
//--------------------------------------------------------------------------------------------------------------------------

    @PutMapping(value = { "/add-images-to-user" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void addImagesToProfile(@RequestPart("id") Long userId,
                                        @RequestPart("imageFile") MultipartFile[] images)
    {
        userService.addImagesToProfile(userId, images);
    }

}
