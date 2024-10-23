package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.TrainingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingHistoryRepository extends JpaRepository<TrainingHistory, Long> { }
