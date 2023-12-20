package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.services.ActivityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/activity")
@AllArgsConstructor

public class ActivityController
{
    ActivityService activityService;


    @GetMapping("/retrieve-all-activities")
    @ResponseBody
    public List<Activity> getAllActivities() { return activityService.retrieveAllActivities(); }


    @GetMapping("/retrieve-activity/{Activity-id}")
    @ResponseBody
    public Activity retrieveActivity(@PathVariable("Activity-id") Long activityId) { return activityService.retrieveActivity(activityId); }


    @PostMapping(value = "/add-activity")
    @ResponseBody
    public Activity addActivity(@RequestBody Activity activity) { return activityService.addActivity(activity); }

    @PutMapping(value = "/update-activity/{Activity-id}")
    @ResponseBody
    public Activity updateActivity(@PathVariable("Activity-id") Long activityId,@RequestBody Activity activity) { return activityService.updateActivity(activityId,activity); }


    @DeleteMapping(value = "/delete-activity/{Activity-id}")
    public void deleteActivity(@PathVariable("Activity-id") Long activityId) { activityService.deleteActivity(activityId); }
}
