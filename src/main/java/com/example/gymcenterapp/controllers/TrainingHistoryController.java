package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.TrainingHistory;
import com.example.gymcenterapp.services.TrainingHistoryService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/training-history")
@RequiredArgsConstructor
public class TrainingHistoryController
{
    private final TrainingHistoryService trainingHistoryService;

    @PostMapping("/create-history/{user-id}")
    public TrainingHistory createHistory(@PathVariable("user-id") Long userId) 
    {
        return trainingHistoryService.addHistory(userId);
    }

    @PutMapping("/update-history/{user-id}")
    public TrainingHistory updateHistory(@PathVariable("user-id") Long userId) 
    {
        return trainingHistoryService.updateHistory(userId);
    }
    
    @GetMapping("/retrieve-all-histories")
    public List<TrainingHistory> retrieveAllHistories() 
    {
        return trainingHistoryService.retrieveAllHistories();
    }

    @GetMapping("/retrieve-history/{id}")
    public TrainingHistory retrieveHistory(@PathVariable("id") Long id) 
    {
        return trainingHistoryService.retrieveHistoryById(id);
    }
    
    @DeleteMapping("/delete-history/{id}")
    public void deleteHistory(@PathVariable("id") Long id) 
    {
        trainingHistoryService.deleteHistory(id);
    }
}
