package com.example.gymcenterapp.services;

import java.util.Date;
import java.util.List;
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
        if (!isGreaterThanOne(userId))
        {
            TrainingHistory trainingHistory = new TrainingHistory();
            User user = userService.retrieveUser(userId);
    
            if (user != null)
            {
                trainingHistory.setUser(user);
                trainingHistory.setCheckInTime(new Date());
                return trainingHistoryRepository.save(trainingHistory);
            }
        }
        
        return null;
    }

    @Override
    public TrainingHistory updateHistory(Long userId) 
    {
        User user = userService.retrieveUser(userId);

        if (user != null)
        {
            List<TrainingHistory> userHistories = trainingHistoryRepository.findByUser(user);

            if (userHistories.size() > 0)
            {
                userHistories.forEach(history -> {
                    if ((history.getIsCheckedIn() == true) && (history.getCheckOutTime() == null))
                    {
                        history.setCheckOutTime(new Date());
                        history.setIsCheckedIn(false);

                        if (trainingHistoryRepository.save(history) != null)
                        {
                            return;
                        }
                    }
                });
            }
        }
        
        return null;
    }

    @Override
    public List<TrainingHistory> retrieveAllHistories() 
    {
        return trainingHistoryRepository.findAll();
    }

    @Override
    public List<User> findDistinctUsers()
    {
        return trainingHistoryRepository.findDistinctUsers();
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

    private Boolean isGreaterThanOne(Long userId)
    {
        User user = userService.retrieveUser(userId);
        List<TrainingHistory> userTrainingHistories = trainingHistoryRepository.findByUser(user);
        Integer number = 1;

        if (userTrainingHistories.size() > 0)
        {
            for (TrainingHistory history : userTrainingHistories) 
            {
                if ((history.getCheckOutTime() == null) && (history.getIsCheckedIn() == true))
                {
                    number = number + 1;
                }

            }
        }

        if (number == 1)
            return false;

        return true;
    }
    
}
