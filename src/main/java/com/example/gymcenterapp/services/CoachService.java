package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.*;
import com.example.gymcenterapp.interfaces.ICoachService;
import com.example.gymcenterapp.repositories.*;
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
    private CoachRepository coachRepository;
    private RoleRepository roleRepository;
    private ActivityRepository activityRepository;
    private EmailServiceImpl emailService;

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

        emailService.sendConfirmationEmail(coach);

        return coachRepository.save(coach);
    }

    @Override
    public List<Coach> retrieveAllCoaches() { return coachRepository.findAll(); }

    @Override
    public Coach retrieveCoach(Long id) { return coachRepository.findById(id).orElse(null); }

    @Override
    public Coach retrieveCoachByEmail(String email) { return coachRepository.findByEmail(email); }

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


    @Override
    public void updateCoachSpecialities(Long coachId, List<Long> specialities)
    {
        for (Long speciality : specialities) 
        {
            addCoachToActivity(coachId, speciality);
        }
    }

    @Override
    public Set<Activity> retrieveCoachSpecialities(Long coachId)
    {
        Coach coach = coachRepository.findById(coachId).orElse(null);
        if (coach != null)
        {
            return coach.getCoachSpecialities();
        }
        return null;
    }

    @Override
    public void addCoachToActivity(Long coachId, Long activityId)
    {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        Coach coach = coachRepository.findById(coachId).orElse(null);
        if ((activity != null) && (coach != null))
        {
            Set<Activity> setActivity = coach.getCoachSpecialities();
            Set<Coach> setCoach = activity.getActCoaches();

            setActivity.add(activity);
            setCoach.add(coach);

            activity.setActCoaches(setCoach);
            coach.setCoachSpecialities(setActivity);

            activityRepository.save(activity);
            coachRepository.save(coach);
            System.out.println("coach added successfully !");
        }
        else
        {
            System.out.println("coach or activity is null in addCoachToActivity");
        }
    }

    @Override
    public void deleteCoachActivities(Long coachId, Long activityId)
    {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        Coach coach = coachRepository.findById(coachId).orElse(null);

        if ((activity != null) && (coach != null))
        {
            Set<Activity> coachSpecialities = coach.getCoachSpecialities();
            Set<Coach> activityCoaches = activity.getActCoaches();

            coachSpecialities.removeIf(act -> act.getActId() == activityId);
            coach.setCoachSpecialities(coachSpecialities);
            coachRepository.save(coach);

            activityCoaches.removeIf(c -> c.getUserId() == coachId);
            activity.setActCoaches(activityCoaches);
            activityRepository.save(activity);

        }
    }

    @Override
    public Set<Session> retrieveCoachSessions(String email)
    {
        Coach coach = coachRepository.findByEmail(email);
        return coach.getCoachSessions();
    }
}
