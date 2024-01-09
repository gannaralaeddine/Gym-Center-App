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
    CoachService coachService;


    @GetMapping("/retrieve-all-coaches")
    @ResponseBody
    public List<Coach> getAllCoaches() { return coachService.retrieveAllCoaches(); }


    @GetMapping("/retrieve-coach/{coach-id}")
    @ResponseBody
    public Coach retrieveCoach(@PathVariable("coach-id") Long coachId) { return coachService.retrieveCoach(coachId); }


    @PostMapping(value = "/add-coach")
    @ResponseBody
    public Coach registerCoach(@RequestBody Coach coach) { return coachService.registerCoach(coach); }

    @PutMapping(value = "/update-coach/{coach-id}")
    @ResponseBody
    public Coach updateCoach(@PathVariable("coach-id") Long coachId,@RequestBody Coach coach) { return coachService.updateCoach(coachId,coach); }


    @DeleteMapping(value = "/delete-coach/{coach-id}")
    public void deleteCoach(@PathVariable("coach-id") Long coachId) { coachService.deleteCoach(coachId); }
}
