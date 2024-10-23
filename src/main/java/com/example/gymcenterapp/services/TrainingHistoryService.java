package com.example.gymcenterapp.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.gymcenterapp.entities.TrainingHistory;
import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.interfaces.ITrainingHistoryService;
import com.example.gymcenterapp.repositories.TrainingHistoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainingHistoryService implements ITrainingHistoryService
{
    private final TrainingHistoryRepository trainingHistoryRepository;
    private final UserService userService;
    @Override
    public TrainingHistory addHistory(Long userId) 
    {
      TrainingHistory trainingHistory = new TrainingHistory();
      User user = userService.retrieveUser(userId);

      if (user != null)
      {
        trainingHistory.setUser(user);
        trainingHistory.setCheckInTime(new Date());
        return trainingHistoryRepository.save(trainingHistory);
      }
      
      return null;
    }

    @Override
    public TrainingHistory updateHistory(TrainingHistory oldHistory, TrainingHistory newHistory) 
    {
        return null;
    }

    @Override
    public List<TrainingHistory> retrieveAllHistories() 
    {
        return trainingHistoryRepository.findAll();
    }

    @Override
    public TrainingHistory retrieveHistoryById(Long id) 
    {
        return trainingHistoryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteHistory(Long id) 
    {
        trainingHistoryRepository.deleteById(id);
    }
    
}
