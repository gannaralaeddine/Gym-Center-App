package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.repositories.ActivityRepository;
import com.example.gymcenterapp.repositories.CategoryRepository;
import com.example.gymcenterapp.services.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/activity")
@AllArgsConstructor

public class ActivityController
{
    ActivityService activityService;

    CategoryRepository categoryRepository;

    ActivityRepository activityRepository;

    @GetMapping("/retrieve-all-activities")
    @ResponseBody
    public List<Activity> getAllActivities() { return activityService.retrieveAllActivities(); }


    @GetMapping("/retrieve-activity/{activity-id}")
    @ResponseBody
    public Activity retrieveActivity(@PathVariable("activity-id") Long activityId) { return activityService.retrieveActivity(activityId); }


    @PostMapping(value = "/add-activity")
    @ResponseBody
    public Activity addActivity(@RequestBody Activity activity) { return activityService.addActivity(activity); }

    @PutMapping(value = "/update-activity/{activity-id}")
    @ResponseBody
    public Activity updateActivity(@PathVariable("activity-id") Long activityId, @RequestBody Activity activity) { return activityService.updateActivity(activityId,activity); }


    @DeleteMapping(value = "/delete-activity/{activity-id}")
    public void deleteActivity(@PathVariable("activity-id") Long activityId) { activityService.deleteActivity(activityId); }

    @PutMapping(value = "/assign-activities/{categoryId}")
    @ResponseBody
    public List<Activity> assignActivitiesToCategory(@PathVariable Long categoryId) { return activityService.assignActivitiesToCategory(categoryId); }
}
