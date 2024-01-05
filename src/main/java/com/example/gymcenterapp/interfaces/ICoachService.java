package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Coach;

import java.util.List;

public interface ICoachService
{
    Coach registerCoach(Coach coach);

    List<Coach> retrieveAllCoaches();

    Coach retrieveCoach(Long id);

    void deleteCoach(Long id);

    Coach updateCoach(Long id, Coach coach);
}
