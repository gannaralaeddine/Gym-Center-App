package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.TrainingHistory;
import com.example.gymcenterapp.entities.User;

import java.util.List;


public interface ITrainingHistoryService
{
    TrainingHistory addHistory (Long userId);

    TrainingHistory updateHistory (Long userId);

    List<TrainingHistory> retrieveAllHistories();

    List<User> findDistinctUsers();

    TrainingHistory retrieveHistoryById(Long id);

    void deleteHistory(Long id);
}
