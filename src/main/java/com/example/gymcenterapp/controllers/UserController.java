package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.EmailModel;
import com.example.gymcenterapp.entities.Role;
import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.services.RoleServiceImpl;
import com.example.gymcenterapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@RestController
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
    public ResponseEntity<String> addUser(@RequestBody User user)
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



// Delete User image
//----------------------------------------------------------------------------------------------------------------------
    @DeleteMapping(value = { "/delete-user-image/{userId}/{imageName}" })
    @ResponseBody
    public User deleteUserImage(@PathVariable Long userId, @PathVariable String imageName)
    {
        return userService.deleteUserImage(userId, imageName);
    }

// Confirm account
//----------------------------------------------------------------------------------------------------------------------
    @RequestMapping("/confirm-account")
    public String confirmUserAccountCompany(@RequestParam("token")String confirmationToken) {
        return userService.confirmUserAccount(confirmationToken);
    }

    @PostMapping(value = "/send-contact-us-email")
    @ResponseBody
    public void sendContactUsEmail(@RequestBody EmailModel email)
    {
        userService.sendContactUsEmail(email);
    }

    @PostMapping(value = "/send-verification-code/{email}")
    @ResponseBody
    public void sendVerificationCode(@PathVariable String email)
    {
        userService.sendVerificationCode(email);
    }


    @PostMapping(value = "/check-verification-code/{code}")
    @ResponseBody
    public ResponseEntity<String> checkVerificationCode(@PathVariable int code)
    {
        return userService.checkVerificationCode(code);
    }


    @PutMapping(value = "/change-password/{email}/{password}")
    @ResponseBody
    public ResponseEntity<String> changePassword(@PathVariable String email, @PathVariable String password)
    {
        return userService.changePassword(email, password);
    }

    @DeleteMapping(value = { "/delete-user/{userId}" })
    @ResponseBody
    public void deleteUserImage(@PathVariable Long userId)
    {
        userService.deleteUser(userId);
    }

}
