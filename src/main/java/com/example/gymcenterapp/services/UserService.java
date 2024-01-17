package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.ImageModel;
import com.example.gymcenterapp.entities.Role;
import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.interfaces.IUserService;
import com.example.gymcenterapp.repositories.RoleRepository;
import com.example.gymcenterapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService implements IUserService, UserDetailsService
{
    final String directory = "C:\\Users\\ganna\\IdeaProjects\\Gym-Center-App\\src\\main\\resources\\static\\users\\";

    UserRepository userRepository;

    RoleRepository roleRepository;

    ImageModelService imageModelService;

    @Override
    public User addUser(User user)
    {
        Set<Role> roles = new HashSet<>();

        user.setUserPassword(new BCryptPasswordEncoder().encode(user.getUserPassword()));

        user.getRoles().forEach(role -> {
            System.out.println("user roles: " + role.getRoleId());
            if (role.getRoleId() != null)
            {
                Role newRole = roleRepository.findById(role.getRoleId()).orElse(null);

                assert newRole != null;
                newRole.getUsers().add(user);

                roles.add(newRole);
            }
            else
            {
                roles.add(role);
            }
        });
        user.setRoles(roles);
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

    @Override
    public int numberOfUsers() {
        return userRepository.numberOfUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        User user = userRepository.findByEmail(email);
        if (user != null)
        {
            return new UserDetailsPrincipal(userRepository.findByEmail(email));
        }
        else
        {
            System.out.println("User by email is null " );
            return null;
        }

    }


    public User retrieveUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }


    @Override
    public User updateUserData(String userEmail, User user)
    {
        User existingUser = userRepository.findByEmail(userEmail);

        if (existingUser != null)
        {
            existingUser.setUserFirstName(user.getUserFirstName());
            existingUser.setUserLastName(user.getUserLastName());
            existingUser.setUserDescription(user.getUserDescription());
            existingUser.setUserBirthDate(user.getUserBirthDate());
            existingUser.setUserPhoneNumber(user.getUserPhoneNumber());
            existingUser.setUserCountry(user.getUserCountry());
            existingUser.setUserState(user.getUserState());
            existingUser.setUserCity(user.getUserCity());
            existingUser.setUserZipCode(user.getUserZipCode());
            existingUser.setUserHeight(user.getUserHeight());
            existingUser.setUserWeight(user.getUserWeight());
            existingUser.setUserGender(user.getUserGender());
            return userRepository.save(existingUser);
        }
        else
        {
            System.out.println("Cannot find user(update user data method)!!!");
            return null;
        }
    }

    @Override
    public User updateProfilePicture(User user, MultipartFile[] file)
    {
        User existingUser = userRepository.findById(user.getUserId()).orElse(null);

        if (existingUser != null)
        {
            String[] imageType = Objects.requireNonNull(file[0].getContentType()).split("/");
            String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
            String filePath = directory + uniqueName;

            try {
                ImageModel imageModel = new ImageModel();
                imageModel.setImageName(uniqueName);
                imageModel.setImageType(file[0].getContentType());
                imageModel.setImageSize(file[0].getSize());
                imageModel.setImageUrl(filePath);

                HashSet<ImageModel> images = new HashSet<>();
                images.add(imageModel);

                existingUser.setUserPicture(uniqueName);
                existingUser.setUserImages(images);

                file[0].transferTo(new File(filePath));

                return userRepository.save(existingUser);
            } catch (Exception e) {
                System.out.println("Error in update profile picture: " + e.getMessage());
                return null;
            }
        }
        return null;
    }
}
