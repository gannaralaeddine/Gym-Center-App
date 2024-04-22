package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @Query(" SELECT user FROM User user WHERE user.userEmail = :email ")
    User findByEmail(String email);

    @Query("SELECT COUNT(*) FROM User")
    int numberOfUsers();

    @Query("SELECT COUNT(*) FROM User user WHERE user.userEmail = :email ")
    int numberOfUsersByEmail(String email);
}
