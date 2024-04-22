package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Coach;
import com.example.gymcenterapp.entities.Session;
import com.example.gymcenterapp.services.CoachService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/coach")
@AllArgsConstructor

public class CoachController
{
    CoachService coachService;


    @GetMapping("/retrieve-all-coaches")
    @ResponseBody
    public List<Coach> getAllCoaches() { return coachService.retrieveAllCoaches(); }


    @GetMapping("/retrieve-coach/{coach-id}")
    @ResponseBody
    public Coach retrieveCoach(@PathVariable("coach-id") Long coachId) { return coachService.retrieveCoach(coachId); }

    @GetMapping("/retrieve-coach-by-email/{email}")
    @ResponseBody
    public Coach retrieveCoachByEmail(@PathVariable("email") String email) { return coachService.retrieveCoachByEmail(email); }


    @GetMapping("/retrieve-coach-specialities/{coach-id}")
    @ResponseBody
    public Set<Activity> retrieveCoachSpecialities(@PathVariable("coach-id") Long coachId) { return coachService.retrieveCoachSpecialities(coachId); }


    @PostMapping(value = "/register-coach")
    @ResponseBody
    public ResponseEntity<String> registerCoach(@RequestBody Coach coach) { return coachService.registerCoach(coach); }

    @PutMapping(value = "/update-coach/{coach-id}")
    @ResponseBody
    public Coach updateCoach(@PathVariable("coach-id") Long coachId,@RequestBody Coach coach) { return coachService.updateCoach(coachId,coach); }


    @DeleteMapping(value = "/delete-coach/{coach-id}")
    public void deleteCoach(@PathVariable("coach-id") Long coachId) { coachService.deleteCoach(coachId); }

    @PutMapping(value = "/add-coach-to-activity/{coach-id}")
    @ResponseBody
    public void updateCoachSpecialities(@PathVariable("coach-id") Long coachId, @RequestBody List<Long> specialities)
    {
        coachService.updateCoachSpecialities(coachId, specialities);
    }

    @DeleteMapping(value = "/delete-coach-activities/{coach-id}/{activity-id}")
    public void deleteCoachActivities(@PathVariable("coach-id") Long coachId, @PathVariable("activity-id") Long activityId)
    {
        coachService.deleteCoachActivities(coachId, activityId);
    }

    @GetMapping("/retrieve-coach-sessions/{email}")
    @ResponseBody
    public Set<Session> retrieveCoachSessions(@PathVariable("email") String email) { return coachService.retrieveCoachSessions(email); }

}
