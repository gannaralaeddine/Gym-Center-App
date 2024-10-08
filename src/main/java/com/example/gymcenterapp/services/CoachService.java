package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.*;
import com.example.gymcenterapp.interfaces.ICoachService;
import com.example.gymcenterapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CoachService implements ICoachService
{
    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    private ConfirmationToken confirmationToken = new ConfirmationToken();


    @Override
    public ResponseEntity<String> registerCoach(Coach coach)
    {
        if (coachRepository.numberOfUsersByEmail(coach.getUserEmail()) == 0)
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

            confirmationToken = emailService.sendConfirmationEmail(coach);

            coachRepository.save(coach);

            return ResponseEntity.status(HttpStatus.OK).body(Long.toString(confirmationToken.getTokenId()));
        }
        return ResponseEntity.status(HttpStatus.FOUND).body("User already exist! please try with another email !");
    }

    @Override
    public List<Coach> retrieveAllCoaches() { return coachRepository.findAll(); }

    @Override
    public Coach retrieveCoach(Long id) { return coachRepository.findById(id).orElse(null); }

    @Override
    public Coach retrieveCoachByEmail(String email) { return coachRepository.findByEmail(email); }

    @Override
    public void deleteCoach(Long id) 
    { 
        Coach coach = coachRepository.findById(id).orElse(null);
        List<ConfirmationToken> confirmationTokenList = confirmationTokenRepository.findAll();
        Boolean isFound = false;
        Integer i = 0;

        if (coach != null)
        {
            coach.getCoachPrivateSessions().forEach((privateSession) -> {
                if (privateSession.getPrivateSessionMember() != null)
                {
                    emailService.sendCancelPrivateSessionEmail(privateSession);
                }
            });
            coach.getCoachSessions().forEach((session) -> {
                if (session.getSessionMembers() != null)
                {
                    emailService.sendCancelSessionEmail(session);
                }
            });
            coach.getCoachSpecialities().forEach((activity) -> activity.setActCoaches(null));
            coach.getCoachSessions().forEach((session) -> {
                session.getSessionMembers().forEach((member) -> {
                    member.setMemberSessions(null);
                });
            });
            coach.setPrivateMembers(null);
            coachRepository.save(coach);

            while (!isFound && i < confirmationTokenList.size() - 1)
            {
                if (confirmationTokenList.get(i).getUser().getUserId() == coach.getUserId())
                {
                    isFound = true;
                    break;
                }
                else
                {
                    i++;
                }
            }
            
            confirmationTokenRepository.deleteById(confirmationTokenList.get(i).getTokenId());
        }
    }
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
    @Transactional
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
    @Transactional
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
    @Transactional
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

    public Set<Member> retrievePrivateMembers(String coachEmail)
    {
        Coach coach = coachRepository.findByEmail(coachEmail);
        return coach.getPrivateMembers();
    }

    public ResponseEntity<String> terminateCoachMemberRelation(String memberEmail, String coachEmail)
    {
        Member member = memberRepository.findByEmail(memberEmail);
        Coach coach = coachRepository.findByEmail(coachEmail);

        if (member != null && coach != null)
        {
            Set<Member> coachMembers = coach.getPrivateMembers();
            Set<Coach> memberCoaches = member.getPrivateCoaches();

            if (coachMembers.contains(member) && memberCoaches.contains(coach))
            {
                coachMembers.remove(member);
                memberCoaches.remove(coach);
                memberRepository.save(member);
                coachRepository.save(coach);

                return new ResponseEntity<>(HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.FOUND).body("Cannot find member for this coach !");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public Set<NotificationMemberCoach> getCoachNotifications(String coachEmail)
    {
        Coach coach = coachRepository.findByEmail(coachEmail);

        return coach.getNotificationMemberCoaches();
    }



    public Set<PrivateSession> getCoachPrivateSessions(String coachEmail)
    {
        Coach coach = coachRepository.findByEmail(coachEmail);
        if (coach != null)
        {
            return coach.getCoachPrivateSessions();
        }
        return null;
    }
}
