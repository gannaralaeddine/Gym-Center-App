package com.example.gymcenterapp;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.Coach;
import com.example.gymcenterapp.entities.Session;
import com.example.gymcenterapp.services.ActivityService;
import com.example.gymcenterapp.services.CategoryService;
import com.example.gymcenterapp.services.CoachService;
import com.example.gymcenterapp.services.SessionService;


@SpringBootTest
public class SessionServiceTest 
{

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CoachService coachService;

    @Test
    public void addSession() 
    {
        List<Coach> coaches = coachService.retrieveAllCoaches();
        List<Activity> activities = activityService.retrieveAllActivities();
        assertNotNull(coaches);
        assertNotNull(activities);
        Session session = sessionService.addSessionWithOneImage(new Session(null, "séance karaté", "description séance karaté", new Date(), 20, 10, null, activities.get(0), null, coaches.get(0), null, null));
        assertNotNull(session);
        session.setSessionCoach(null);
        session.setSessionActivity(null);
        sessionService.deleteSession(sessionService.addSessionWithOneImage(session).getSessionId());
    }

    @Test
    public void retrieveAllSessions() { assertNotNull(sessionService.retrieveAllSessions()); }

    @Test
    public void retrieveSession() 
    { 
        List<Coach> coaches = coachService.retrieveAllCoaches();
        List<Activity> activities = activityService.retrieveAllActivities();
        assertNotNull(coaches);
        assertNotNull(activities);
        Session session = sessionService.addSessionWithOneImage(new Session(null, "séance karaté", "description séance karaté", new Date(), 20, 10, null, activities.get(0), null, coaches.get(0), null, null));
        assertNotNull(sessionService.retrieveSession(session.getSessionId()));
        session.setSessionCoach(null);
        session.setSessionActivity(null);
        sessionService.deleteSession(sessionService.addSessionWithOneImage(session).getSessionId());
    }

    @Test
    public void updateSession()
    {
        List<Coach> coaches = coachService.retrieveAllCoaches();
        List<Activity> activities = activityService.retrieveAllActivities();
        assertNotNull(coaches);
        assertNotNull(activities);
        Session session = sessionService.addSessionWithOneImage(new Session(null, "séance karaté", "description séance karaté", new Date(), 20, 10, null, activities.get(0), null, coaches.get(0), null, null));
        assertNotNull(session);
        session = sessionService.updateSession(session.getSessionId(), new Session(null, "séance body building", "description séance body  building", new Date(), 20, 10, null, activities.get(0), null, coaches.get(0), null, null));
        assertNotNull(session);
        session.setSessionCoach(null);
        session.setSessionActivity(null);
        sessionService.deleteSession(sessionService.addSessionWithOneImage(session).getSessionId());
    }

    @Test
    public void deleteSession()
    {
        addSession();
    }
}
