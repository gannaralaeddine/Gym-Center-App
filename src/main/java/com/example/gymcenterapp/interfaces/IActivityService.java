package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Activity;

import java.util.List;

public interface IActivityService
{
    Activity addActivity(Activity activity);

    List<Activity> retrieveAllActivities();

    Activity retrieveActivity(Long id);
    
    void deleteActivity(Long id);

    Activity updateActivity(Long id, Activity activity);
}
