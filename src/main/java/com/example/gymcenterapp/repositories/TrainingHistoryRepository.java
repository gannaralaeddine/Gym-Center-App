package com.example.gymcenterapp.repositories;


import com.example.gymcenterapp.entities.TrainingHistory;
import com.example.gymcenterapp.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TrainingHistoryRepository extends JpaRepository<TrainingHistory, Long> 
{
    List<TrainingHistory> findByUser(User user);

    @Query("SELECT DISTINCT history.user FROM TrainingHistory history")
    List<User> findDistinctUsers();
}
