package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Role;
import com.example.gymcenterapp.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class RoleServiceImpl
{
    private RoleRepository roleRepository;

    public Role addRole(Role role)
    {
        return roleRepository.save(role);
    }

    public List<Role> retrieveAllRoles()
    {
        return roleRepository.findAll();
    }
}
