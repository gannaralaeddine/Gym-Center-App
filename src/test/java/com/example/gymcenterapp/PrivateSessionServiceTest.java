package com.example.gymcenterapp;

import static org.junit.Assert.assertNotNull;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gymcenterapp.entities.Coach;
import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.entities.PrivateSession;
import com.example.gymcenterapp.repositories.PrivateSessionRepository;
import com.example.gymcenterapp.services.ActivityService;
import com.example.gymcenterapp.services.CategoryService;
import com.example.gymcenterapp.services.CoachService;
import com.example.gymcenterapp.services.MemberService;
import com.example.gymcenterapp.services.PrivateSessionService;


@SpringBootTest
public class PrivateSessionServiceTest
{

    @Autowired
    private PrivateSessionService privateSessionService;

    @Autowired
    private PrivateSessionRepository privateSessionRepository;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CoachService coachService;

    @Autowired
    private MemberService memberService;

    @Test
    public void addPrivateSession() 
    {
        List<Coach> coaches = coachService.retrieveAllCoaches();
        List<Member> members = memberService.retrieveAllMembers();
        assertNotNull(coaches);
        assertNotNull(members);
        PrivateSession privateSession = new PrivateSession(null, 
        "titre de la séance privée", 
        new Date(), 
        new Date(new Date().getTime() + 86400000), 
        false, 
        members.get(0), 
        coaches.get(0));
        privateSession =  privateSessionService.addPrivateSession(privateSession);
        assertNotNull(privateSession);
        privateSession.setPrivateSessionCoach(null);
        privateSession.setPrivateSessionMember(null);
        privateSession = privateSessionService.addPrivateSession(privateSession);
        privateSessionRepository.deleteById(privateSession.getPrivateSessionId());
    }

    @Test
    public void retrieveAvailablePrivateSessions() { assertNotNull(privateSessionService.retrieveAvailablePrivateSessions()); }

    @Test
    public void retrievePrivateSession() 
    {
        List<Coach> coaches = coachService.retrieveAllCoaches();
        List<Member> members = memberService.retrieveAllMembers();
        assertNotNull(coaches);
        assertNotNull(members);
        PrivateSession privateSession = new PrivateSession(null, 
        "titre de la séance privée", 
        new Date(), 
        new Date(new Date().getTime() + 86400000), 
        false, 
        members.get(0), 
        coaches.get(0));
        privateSession =  privateSessionService.addPrivateSession(privateSession);

        assertNotNull(privateSessionService.retrievePrivateSession(privateSession.getPrivateSessionId()));
        
        privateSession.setPrivateSessionCoach(null);
        privateSession.setPrivateSessionMember(null);
        privateSession = privateSessionService.addPrivateSession(privateSession);
        privateSessionRepository.deleteById(privateSession.getPrivateSessionId());
    }

    @Test
    public void cancelPrivateSession()
    {
        List<Coach> coaches = coachService.retrieveAllCoaches();
        List<Member> members = memberService.retrieveAllMembers();
        assertNotNull(coaches);
        assertNotNull(members);
        PrivateSession privateSession = new PrivateSession(null, 
        "titre de la séance privée", 
        new Date(), 
        new Date(new Date().getTime() + 86400000), 
        false, 
        members.get(0), 
        coaches.get(0));
        privateSession =  privateSessionService.addPrivateSession(privateSession);
        assertNotNull(privateSession);

        privateSessionService.cancelPrivateSession(privateSession.getPrivateSessionMember().getUserEmail(), privateSession.getPrivateSessionId());

        privateSession.setPrivateSessionCoach(null);
        privateSession.setPrivateSessionMember(null);
        privateSession = privateSessionService.addPrivateSession(privateSession);
        privateSessionRepository.deleteById(privateSession.getPrivateSessionId());
    }
}
