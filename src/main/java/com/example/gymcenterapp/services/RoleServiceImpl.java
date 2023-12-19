package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Role;
import com.example.gymcenterapp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl
{
    @Autowired
    RoleRepository roleRepository;

    public Role addRole(Role role)
    {
        return roleRepository.save(role);
    }
}
