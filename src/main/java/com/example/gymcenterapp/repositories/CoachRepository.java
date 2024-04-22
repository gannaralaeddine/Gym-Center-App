package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {

    @Query("select user from Coach user where user.userEmail = :email ")
    Coach findByEmail(String email);

    @Query("SELECT COUNT(*) FROM Coach user WHERE user.userEmail = :email ")
    int numberOfUsersByEmail(String email);

}
