package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Coach;

import java.util.List;
import java.util.Set;

public interface ICoachService
{
    Coach registerCoach(Coach coach);

    List<Coach> retrieveAllCoaches();

    Coach retrieveCoach(Long id);

    void deleteCoach(Long id);

    Coach updateCoach(Long id, Coach coach);

    void updateCoachSpecialities(Long coachId, List<Long> specialities);

    Set<Activity> retrieveCoachSpecialities(Long coachId);

    void addCoachToActivity(Long coachId, Long activityId);

    void deleteCoachActivities(Long coachId, Long activityId);
}
