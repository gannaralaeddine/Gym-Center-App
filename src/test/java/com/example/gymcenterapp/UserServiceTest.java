package com.example.gymcenterapp;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Coach;
import com.example.gymcenterapp.entities.ConfirmationToken;
import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.services.ActivityService;
import com.example.gymcenterapp.services.CoachService;
import com.example.gymcenterapp.services.UserService;
import com.example.gymcenterapp.repositories.ConfirmationTokenRepository;
import com.example.gymcenterapp.repositories.UserRepository;

@SpringBootTest
public class UserServiceTest 
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Test
    public void addUser()
    {
        User user = new User();
        user.setUserEmail("hello@gmail.com");
        user.setUserFirstName("ghassen");
        user.setUserLastName("awadi");
        user.setUserDescription("description de l'utilisateur");
        user.setUserGender("Homme");
        user.setUserPassword("000000");
        user.setUserIsSubscribed(false);
        user.setUserIsEnabled(false);
        ResponseEntity<String> confirmationTokResponseEntity = userService.addUser(user);
        assertNotNull(confirmationTokResponseEntity);
        confirmationTokenRepository.deleteById(Long.valueOf(confirmationTokResponseEntity.getBody()));
    }

    @Test
    public void changePassword()
    {
        User user = new User();
        user.setUserEmail("hello@gmail.com");
        user.setUserFirstName("ghassen");
        user.setUserLastName("awadi");
        user.setUserDescription("description de l'utilisateur");
        user.setUserGender("Homme");
        user.setUserPassword("000000");
        user.setUserIsSubscribed(false);
        user.setUserIsEnabled(false);
        String oldPassword = user.getUserPassword();
        ResponseEntity<String> confirmationTokResponseEntity = userService.addUser(user);
        assertNotNull(confirmationTokResponseEntity);
        user.setUserPassword("111111");
        assertNotEquals("User not found !", userService.changePassword(user.getUserEmail(), user.getUserPassword()).getBody());
        user = userService.retrieveUser(user.getUserId());
        assertNotEquals(oldPassword, user.getUserPassword());
        confirmationTokenRepository.deleteById(Long.valueOf(confirmationTokResponseEntity.getBody()));
    }

    @Test
    public void confirmUserAccount()
    {
        User user = new User();
        user.setUserEmail("hello@gmail.com");
        user.setUserFirstName("ghassen");
        user.setUserLastName("awadi");
        user.setUserDescription("description de l'utilisateur");
        user.setUserGender("Homme");
        user.setUserPassword("000000");
        user.setUserIsSubscribed(false);
        user.setUserIsEnabled(false);
        ResponseEntity<String> confirmationTokResponseEntity = userService.addUser(user);
        ConfirmationToken confirmationToken = confirmationTokenRepository.findById(Long.valueOf(confirmationTokResponseEntity.getBody())).orElse(null);
        assertNotNull(confirmationToken);
        assertNotEquals("<h1>Invalid token!</h1>",userService.confirmUserAccount(confirmationToken.getConfirmationToken()));
        confirmationTokenRepository.deleteById(confirmationToken.getTokenId());
    }

    @Test
    public void updateUserData()
    {
        User user = new User();
        user.setUserEmail("hello@gmail.com");
        user.setUserFirstName("ghassen");
        user.setUserLastName("awadi");
        user.setUserDescription("description de l'utilisateur");
        user.setUserGender("Homme");
        user.setUserPassword("000000");
        user.setUserIsSubscribed(false);
        user.setUserIsEnabled(false);
        ResponseEntity<String> confirmationTokResponseEntity = userService.addUser(user);
        assertNotNull(confirmationTokResponseEntity);
        user.setUserEmail("awadifiras@gmail.com");
        user.setUserFirstName("firas");
        user.setUserLastName("awadi");
        user = userRepository.save(user);
        confirmationTokenRepository.deleteById(Long.valueOf(confirmationTokResponseEntity.getBody()));
    }

    @Test
    public void retrieveAllUsers() { assertNotNull(userService.retrieveAllUsers()); }

    @Test
    public void retrieveUser()
    {
        User user = new User();
        user.setUserEmail("hello@gmail.com");
        user.setUserFirstName("ghassen");
        user.setUserLastName("awadi");
        user.setUserDescription("description de l'utilisateur");
        user.setUserGender("Homme");
        user.setUserPassword("000000");
        user.setUserIsSubscribed(false);
        user.setUserIsEnabled(false);
        ResponseEntity<String> confirmationTokResponseEntity = userService.addUser(user);
        ConfirmationToken confirmationToken = confirmationTokenRepository.findById(Long.valueOf(confirmationTokResponseEntity.getBody())).orElse(null);
        assertNotNull(userService.retrieveUser(confirmationToken.getUser().getUserId()));
        confirmationTokenRepository.deleteById(Long.valueOf(confirmationTokResponseEntity.getBody()));
    }
    

}
