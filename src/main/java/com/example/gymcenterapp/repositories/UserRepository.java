package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @Query(" select user from User user where user.userEmail = :email ")
    User findByEmail(String email);

    @Query("SELECT COUNT(*) FROM User")
    int numberOfUsers();

}
