package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.repositories.ActivityRepository;
import com.example.gymcenterapp.repositories.CategoryRepository;
import com.example.gymcenterapp.services.ActivityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
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



    @PutMapping("/assignCategoryToActivity/{categoryId}/{activityId}")
    Activity assignCategoryToActivity(
            @PathVariable Long categoryId,
            @PathVariable Long activityId
    ) {
        Activity activity = activityRepository.findById(activityId).get();
        Category category = categoryRepository.findById(categoryId).get();
        activity.setCategory(category);

        List<Activity> activities = category.getCatActivities();
        activities.add(activity);

        category.setCatActivities(activities);

        categoryRepository.save(category);
        return activityRepository.save(activity);
    }
}
