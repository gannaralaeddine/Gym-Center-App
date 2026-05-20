package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.Role;
import com.example.gymcenterapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{

    @Query(" SELECT role FROM Role role WHERE role.roleName = :roleName ")
    Role findByRoleName(String roleName);
}
