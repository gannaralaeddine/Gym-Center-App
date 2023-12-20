package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Coach;
import com.example.gymcenterapp.services.CoachService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coach")
@AllArgsConstructor

public class CoachController
{
    CoachService CoachService;


    @GetMapping("/retrieve-all-coaches")
    @ResponseBody
    public List<Coach> getAllActivities() { return CoachService.retrieveAllCoaches(); }


    @GetMapping("/retrieve-coach/{coach-id}")
    @ResponseBody
    public Coach retrieveCoach(@PathVariable("coach-id") Long coachId) { return CoachService.retrieveCoach(coachId); }


    @PostMapping(value = "/add-coach")
    @ResponseBody
    public Coach addCoach(@RequestBody Coach coach) { return CoachService.addCoach(coach); }

    @PutMapping(value = "/update-coach/{coach-id}")
    @ResponseBody
    public Coach updateCoach(@PathVariable("coach-id") Long coachId,@RequestBody Coach coach) { return CoachService.updateCoach(coachId,coach); }


    @DeleteMapping(value = "/delete-coach/{coach-id}")
    public void deleteCoach(@PathVariable("coach-id") Long coachId) { CoachService.deleteCoach(coachId); }
}
