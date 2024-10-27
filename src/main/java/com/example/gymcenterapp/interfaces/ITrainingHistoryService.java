package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.TrainingHistory;
import java.util.List;


public interface ITrainingHistoryService
{
    TrainingHistory addHistory (Long userId);

    TrainingHistory updateHistory (Long userId);

    List<TrainingHistory> retrieveAllHistories();

    TrainingHistory retrieveHistoryById(Long id);

    void deleteHistory(Long id);
}
