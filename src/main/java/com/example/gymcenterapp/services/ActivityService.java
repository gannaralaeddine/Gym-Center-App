package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.interfaces.IActivityService;
import com.example.gymcenterapp.repositories.ActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

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
            //existingActivity.setActCoaches(activity.getActCoaches());
            return activityRepository.save(existingActivity);
        }

        return null;
    }
}
