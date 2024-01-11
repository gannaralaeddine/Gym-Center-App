package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Coach;
import com.example.gymcenterapp.entities.Role;
import com.example.gymcenterapp.interfaces.ICoachService;
import com.example.gymcenterapp.repositories.CoachRepository;
import com.example.gymcenterapp.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor

public class CoachService implements ICoachService
{ 
    CoachRepository coachRepository;
    RoleRepository roleRepository;

    @Override
    public Coach registerCoach(Coach coach)
    {
        Set<Role> roles = new HashSet<>();

        coach.setUserPassword(new BCryptPasswordEncoder().encode(coach.getUserPassword()));

        coach.getRoles().forEach(role -> {

            if (role.getRoleId() != null)
            {
                Role newRole = roleRepository.findById(role.getRoleId()).orElse(null);

                assert newRole != null;
                newRole.getUsers().add(coach);

                roles.add(newRole);
            }
            else
            {
                roles.add(role);
            }
        });
        coach.setRoles(roles);
        return coachRepository.save(coach);
    }

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
            existingCoach.setUserEmail(coach.getUserEmail());
            existingCoach.setUserBirthDate(coach.getUserBirthDate());
            existingCoach.setUserPhoneNumber(coach.getUserPhoneNumber());
            existingCoach.setUserCity(coach.getUserCity());
            existingCoach.setUserState(coach.getUserCountry());
            existingCoach.setUserCountry(coach.getUserCountry());
            existingCoach.setUserGender(coach.getUserGender());
            existingCoach.setUserHeight(coach.getUserHeight());
            existingCoach.setUserWeight(coach.getUserWeight());
            existingCoach.setUserPicture(coach.getUserPicture());
            existingCoach.setUserZipCode(coach.getUserZipCode());
            existingCoach.setUserPassword(coach.getUserPassword());
            existingCoach.setRoles(coach.getRoles());
            existingCoach.setCoachSessions(coach.getCoachSessions());
            existingCoach.setCoachSpecialities(coach.getCoachSpecialities());
            return coachRepository.save(existingCoach);
        }

        return null;
    }
}
