package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.Activity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> { 
    @Query("SELECT act FROM Activity act WHERE act.category.catId = :categoryId")
    List<Activity> getCategoryActivities (Long categoryId);
    
}
