package com.example.gymcenterapp;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Coach;
import com.example.gymcenterapp.entities.ConfirmationToken;
import com.example.gymcenterapp.services.ActivityService;
import com.example.gymcenterapp.services.CoachService;
import com.example.gymcenterapp.repositories.ConfirmationTokenRepository;

@SpringBootTest
public class CoachServiceTest 
{
    @Autowired
    private CoachService coachService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Test
    public void registerCoach() 
    {
        Coach coach = new Coach();
        coach.setUserEmail("hello@gmail.com");
        coach.setUserFirstName("ghassen");
        coach.setUserLastName("awadi");
        coach.setUserDescription("description de l'utilisateur");
        coach.setUserGender("Homme");
        coach.setUserPassword("000000");
        coach.setUserIsSubscribed(false);
        coach.setUserIsEnabled(false);
        ResponseEntity<String> confirmationTokenResponseEntity = coachService.registerCoach(coach);
        assertNotNull(confirmationTokenResponseEntity);
        confirmationTokenRepository.deleteById(Long.valueOf(confirmationTokenResponseEntity.getBody()));
    }

    @Test
    public void retrieveCoachByEmail()
    {
        Coach coach = new Coach();
        coach.setUserEmail("hello@gmail.com");
        coach.setUserFirstName("ghassen");
        coach.setUserLastName("awadi");
        coach.setUserDescription("description de l'utilisateur");
        coach.setUserGender("Homme");
        coach.setUserPassword("000000");
        coach.setUserIsSubscribed(false);
        coach.setUserIsEnabled(false);
        ResponseEntity<String> confirmationTokenResponseEntity = coachService.registerCoach(coach);
        assertNotNull(confirmationTokenResponseEntity);
        ConfirmationToken confirmationToken = confirmationTokenRepository.findById(Long.valueOf(confirmationTokenResponseEntity.getBody())).orElse(null);
        if (confirmationToken != null)
        {
            coach = coachService.retrieveCoach(confirmationToken.getUser().getUserId());
            assertNotNull(coach);
            confirmationTokenRepository.deleteById(Long.valueOf(confirmationTokenResponseEntity.getBody()));
        }
    }

    @Test
    public void retrieveAllCoaches() { assertNotNull(coachService.retrieveAllCoaches()); }

    @Test
    public void updateCoach() 
    {
        Coach coach = new Coach();
        coach.setUserEmail("hello@gmail.com");
        coach.setUserFirstName("ghassen");
        coach.setUserLastName("awadi");
        coach.setUserDescription("description de l'utilisateur");
        coach.setUserGender("Homme");
        coach.setUserPassword("000000");
        coach.setUserIsSubscribed(false);
        coach.setUserIsEnabled(false);
        ResponseEntity<String> confirmationTokenResponseEntity = coachService.registerCoach(coach);
        assertNotNull(confirmationTokenResponseEntity);

        ConfirmationToken confirmationToken = confirmationTokenRepository.findById(Long.valueOf(confirmationTokenResponseEntity.getBody())).orElse(null);
        if (confirmationToken != null)
        {
            coach = coachService.retrieveCoach(confirmationToken.getUser().getUserId());
            if (coach != null)
            {
                coach.setUserEmail("gannarale@gmail.com");
                coach.setUserFirstName("ala");
                coach.setUserLastName("gannar");
                coach = coachService.updateCoach(confirmationToken.getUser().getUserId(), coach);
                assertNotNull(coach);
            }
        }

        confirmationTokenRepository.deleteById(Long.valueOf(confirmationTokenResponseEntity.getBody()));
    }

    @Test
    public void updateCoachSpecialities() 
    {
        Coach coach = new Coach();
        coach.setUserEmail("hello@gmail.com");
        coach.setUserFirstName("ghassen");
        coach.setUserLastName("awadi");
        coach.setUserDescription("description de l'utilisateur");
        coach.setUserGender("Homme");
        coach.setUserPassword("000000");
        coach.setUserIsSubscribed(false);
        coach.setUserIsEnabled(false);
        ResponseEntity<String> confirmationTokenResponseEntity = coachService.registerCoach(coach);
        assertNotNull(confirmationTokenResponseEntity);

        ConfirmationToken confirmationToken = confirmationTokenRepository.findById(Long.valueOf(confirmationTokenResponseEntity.getBody())).orElse(null);
        if (confirmationToken != null)
        {
            coach = coachService.retrieveCoach(confirmationToken.getUser().getUserId());
            if (coach != null)
            {
                List<Activity> activities = activityService.retrieveAllActivities();
                List<Long> specialities = new ArrayList<>();
                specialities.add(activities.get(0).getActId());
                assertNotNull(activities);
                coachService.updateCoachSpecialities(coach.getUserId(), specialities);
                coach.setCoachSpecialities(null);
                assertNotNull(coachService.updateCoach(coach.getUserId(), coach));
            }
        }
            
        confirmationTokenRepository.deleteById(Long.valueOf(confirmationTokenResponseEntity.getBody()));
    }

    @Test
    public void addCoachToActivity() 
    {
        List<Activity> activities = activityService.retrieveAllActivities();
        Coach coach = new Coach();
        coach.setUserEmail("hello@gmail.com");
        coach.setUserFirstName("ghassen");
        coach.setUserLastName("awadi");
        coach.setUserDescription("description de l'utilisateur");
        coach.setUserGender("Homme");
        coach.setUserPassword("000000");
        coach.setUserIsSubscribed(false);
        coach.setUserIsEnabled(false);
        ResponseEntity<String> confirmationTokenResponseEntity = coachService.registerCoach(coach);
        assertNotNull(confirmationTokenResponseEntity);

        ConfirmationToken confirmationToken = confirmationTokenRepository.findById(Long.valueOf(confirmationTokenResponseEntity.getBody())).orElse(null);
        if (confirmationToken != null)
        {
            coachService.addCoachToActivity(confirmationToken.getUser().getUserId(), activities.get(0).getActId());  
            coach.setCoachSpecialities(null);
            assertNotNull(coachService.updateCoach(confirmationToken.getUser().getUserId(), coach)); 
        }

        confirmationTokenRepository.deleteById(Long.valueOf(confirmationTokenResponseEntity.getBody()));
    }

    @Test
    public void retrieveCoachSpecialities() 
    {
        List<Coach> coaches = coachService.retrieveAllCoaches();
        assertNotNull(coaches);
        assertNotNull(coachService.retrieveCoachSpecialities(coaches.get(0).getUserId()));
    }

    @Test
    public void deleteCoachActivities() 
    {
        List<Activity> activities = activityService.retrieveAllActivities();
        Coach coach = new Coach();
        coach.setUserEmail("hello@gmail.com");
        coach.setUserFirstName("ghassen");
        coach.setUserLastName("awadi");
        coach.setUserDescription("description de l'utilisateur");
        coach.setUserGender("Homme");
        coach.setUserPassword("000000");
        coach.setUserIsSubscribed(false);
        coach.setUserIsEnabled(false);
        ResponseEntity<String> confirmationTokenResponseEntity = coachService.registerCoach(coach);
        assertNotNull(confirmationTokenResponseEntity);

        ConfirmationToken confirmationToken = confirmationTokenRepository.findById(Long.valueOf(confirmationTokenResponseEntity.getBody())).orElse(null);
        if (confirmationToken != null)
        {
            coachService.addCoachToActivity(confirmationToken.getUser().getUserId(), activities.get(0).getActId());  
            coachService.deleteCoachActivities(confirmationToken.getUser().getUserId(), activities.get(0).getActId());  
        }

        confirmationTokenRepository.deleteById(Long.valueOf(confirmationTokenResponseEntity.getBody()));
    }
    
    @Test
    public void retrieveCoachSessions()
    {
        List<Coach> coaches = coachService.retrieveAllCoaches();
        assertNotNull(coachService.retrieveCoachSessions(coaches.get(0).getUserEmail()));
    }

    @Test
    public void retrievePrivateMembers()
    {
        List<Coach> coaches = coachService.retrieveAllCoaches();
        assertNotNull(coachService.retrievePrivateMembers(coaches.get(0).getUserEmail()));
    }

    @Test
    public void retrieveCoachPrivateSessions()
    {
        List<Coach> coaches = coachService.retrieveAllCoaches();
        assertNotNull(coachService.retrieveCoachSessions(coaches.get(0).getUserEmail()));
    }
}
