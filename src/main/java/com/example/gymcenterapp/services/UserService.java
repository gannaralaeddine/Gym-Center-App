package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.ImageModel;
import com.example.gymcenterapp.entities.Role;
import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.interfaces.IUserService;
import com.example.gymcenterapp.repositories.ImageModelRepository;
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

    ImageModelRepository imageModelRepository;

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
    public User updateProfilePicture(User user, MultipartFile[] files) {
        User existingUser = userRepository.findByEmail(user.getUserEmail());

        System.out.println("getUserPicture: " + user.getUserPicture());

        if (existingUser != null)
        {
            if (user.getUserPicture() != null)
            {
                updateProfileImage(files, existingUser);
            }
            else
            {
                addProfileImage(files, existingUser);
            }
        }
        return null;
    }


    @Override
    public void addImagesToProfile(Long userId, MultipartFile[] files)
    {
        try
        {
            User existingUser = userRepository.findById(userId).orElse(null);

            if (existingUser != null)
            {
                Set<ImageModel> images =  imageModelService.prepareFiles(files, existingUser.getUserImages(), directory);

                existingUser.setUserImages(images);

                userRepository.save(existingUser);
            }

        }
        catch (Exception e)
        {
            System.out.println("Error in add Images To user profile: " + e.getMessage());
        }
    }

    public void updateProfileImage(MultipartFile[] files, User existingUser)
    {
        String[] imageType = Objects.requireNonNull(files[0].getContentType()).split("/");
        String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
        String filePath = directory + uniqueName;

        ImageModel imageModel = new ImageModel();
        imageModel.setImageName(uniqueName);
        imageModel.setImageType(files[0].getContentType());
        imageModel.setImageSize(files[0].getSize());
        imageModel.setImageUrl(filePath);

        try
        {
            Set<ImageModel> existingImages = existingUser.getUserImages();
            existingImages.add(imageModel);

            files[0].transferTo(new File(filePath));


            ImageModel existingImageModel = imageModelService.findImageByName(existingUser.getUserPicture());
            existingImages.remove(existingImageModel);
            imageModelRepository.delete(existingImageModel);
            imageModelService.removeFile(directory, existingUser.getUserPicture());

            existingUser.setUserPicture(uniqueName);
            existingUser.setUserImages(existingImages);
            userRepository.save(existingUser);

        }
        catch (Exception e) {
            System.out.println("Error in update profile picture: " + e.getMessage());
        }
    }

    public void addProfileImage(MultipartFile[] files, User existingUser)
    {
        String[] imageType = Objects.requireNonNull(files[0].getContentType()).split("/");
        String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
        String filePath = directory + uniqueName;

        ImageModel imageModel = new ImageModel();
        imageModel.setImageName(uniqueName);
        imageModel.setImageType(files[0].getContentType());
        imageModel.setImageSize(files[0].getSize());
        imageModel.setImageUrl(filePath);

        try
        {
            existingUser.getUserImages().add(imageModel);

            files[0].transferTo(new File(filePath));
            existingUser.setUserPicture(uniqueName);
            userRepository.save(existingUser);
        }
        catch (Exception e)
        {
            System.out.println("Error in add Images To Activity: " + e.getMessage());
        }
    }
}
