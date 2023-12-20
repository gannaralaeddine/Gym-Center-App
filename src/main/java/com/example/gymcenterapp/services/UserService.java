package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Role;
import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.interfaces.IUserService;
import com.example.gymcenterapp.repositories.RoleRepository;
import com.example.gymcenterapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService, UserDetailsService
{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public User addUser(User user)
    {
        Set<Role> roles = new HashSet<>();

        user.setUserPassword(new BCryptPasswordEncoder().encode(user.getUserPassword()));

        user.getRoles().forEach(role -> {
            System.out.println("user roles: " + role.getRoleId());
            if (role.getRoleId() != null)
            {
                Role newRole = roleRepository.findById(role.getRoleId()).get();

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return new UserDetailsPrincipal(userRepository.findByUsername(username));
    }
}
