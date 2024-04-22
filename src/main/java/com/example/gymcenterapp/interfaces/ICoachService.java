package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Coach;
import com.example.gymcenterapp.entities.Session;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface ICoachService
{
    ResponseEntity<String> registerCoach(Coach coach);

    List<Coach> retrieveAllCoaches();

    Coach retrieveCoach(Long id);

    Coach retrieveCoachByEmail(String email);

    void deleteCoach(Long id);

    Coach updateCoach(Long id, Coach coach);

    void updateCoachSpecialities(Long coachId, List<Long> specialities);

    Set<Activity> retrieveCoachSpecialities(Long coachId);

    void addCoachToActivity(Long coachId, Long activityId);

    void deleteCoachActivities(Long coachId, Long activityId);

    Set<Session> retrieveCoachSessions(String email);
}
