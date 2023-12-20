package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Coach;
import com.example.gymcenterapp.interfaces.ICoachService;
import com.example.gymcenterapp.repositories.CoachRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor

public class CoachService implements ICoachService
{ 
    CoachRepository coachRepository;

    @Override
    public Coach addCoach(Coach coach) { return coachRepository.save(coach); }

    @Override
    public List<Coach> retrieveAllCoaches() { return coachRepository.findAll(); }

    @Override
    public Coach retrieveCoach(Long id) { return coachRepository.findById(id).orElse(null); }

    @Override
    public void deleteCoach(Long id) { coachRepository.deleteById(id);}

    @Override
    public Coach updateCoach(Long id, Coach coach)
    {
        Coach existingCoach = coachRepository.findById(id).orElse(null);

        if (existingCoach != null)
        {
            existingCoach.setCoachSessions(coach.getCoachSessions());
            existingCoach.setCoachSpecialities(coach.getCoachSpecialities());
            return coachRepository.save(existingCoach);
        }

        return null;
    }
}
