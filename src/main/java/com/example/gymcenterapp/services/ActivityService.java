package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.interfaces.IActivityService;
import com.example.gymcenterapp.repositories.ActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor

public class ActivityService implements IActivityService
{ 
    ActivityRepository activityRepository;

    @Override
    public Activity addActivity(Activity activity) { return activityRepository.save(activity); }

    @Override
    public List<Activity> retrieveAllActivities() { return activityRepository.findAll(); }

    @Override
    public Activity retrieveActivity(Long id) { return activityRepository.findById(id).orElse(null); }

    @Override
    public void deleteActivity(Long id) { activityRepository.deleteById(id);}

    @Override
    public Activity updateActivity(Long id, Activity activity)
    {
        Activity existingActivity = activityRepository.findById(id).orElse(null);

        if (existingActivity != null)
        {
            existingActivity.setActName(activity.getActName());
            existingActivity.setActDescription(activity.getActDescription());
            existingActivity.setActImage(activity.getActImage());
            existingActivity.setCategory(activity.getCategory());
            return activityRepository.save(existingActivity);
        }

        return null;
    }

    @Override
    public List<Activity> assignActivitiesToCategory(Long categoryId) 
    {
        List<Activity> assignedActivities = new ArrayList<Activity>();// = new HashSet<Activity>();
        List<Activity> listActivities = activityRepository.findAll();
        for (Activity activity : listActivities) 
        {
            if (activity.getCategory().getCatId() == categoryId)
                assignedActivities.add(activity);
            
        }
        return assignedActivities;
    }


}
