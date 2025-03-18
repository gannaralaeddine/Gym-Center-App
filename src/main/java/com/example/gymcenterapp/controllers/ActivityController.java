package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Offer;
import com.example.gymcenterapp.repositories.ActivityRepository;
import com.example.gymcenterapp.repositories.CategoryRepository;
import com.example.gymcenterapp.services.ActivityService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor

public class ActivityController {

    private final ActivityService activityService;

    /*
     * CategoryRepository categoryRepository;
     * 
     * ActivityRepository activityRepository;
     */

    @GetMapping("/retrieve-all-activities")
    @ResponseBody
    // @RolesAllowed({ "ROLE_COACH", "ROLE_USER" })
    public List<Activity> getAllActivities() {
        return activityService.retrieveAllActivities();
    }

    @GetMapping("/retrieve-activity/{activity-id}")
    @ResponseBody
    public Activity retrieveActivity(@PathVariable("activity-id") Long activityId) {
        return activityService.retrieveActivity(activityId);
    }

    @GetMapping("/retrieve-activity-offers/{activity-id}")
    @ResponseBody
    public Set<Offer> retrieveActivityOffers(@PathVariable("activity-id") Long activityId) {
        return activityService.retrieveActivityOffers(activityId);
    }

    @PostMapping(value = "/add-activity")
    @ResponseBody
    public Activity addActivity(@RequestBody Activity activity) {
        return activityService.addActivity(activity);
    }

    @PutMapping(value = "/update-activity/{activity-id}")
    @ResponseBody
    public Activity updateActivity(@PathVariable("activity-id") Long activityId, @RequestBody Activity activity) {
        return activityService.updateActivity(activityId, activity);
    }

    @DeleteMapping(value = "/delete-activity/{activity-id}")
    public void deleteActivity(@PathVariable("activity-id") Long activityId) {
        activityService.deleteActivity(activityId);
    }

    @GetMapping(value = "/get-category-activities/{categoryId}")
    @ResponseBody
    public List<Activity> getCategoryActivities(@PathVariable Long categoryId) {
        return activityService.getCategoryActivities(categoryId);
    }

    // Add Category with one image
    // --------------------------------------------------------------------------------------------------------------------------

    @PostMapping(value = { "/create-activity" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Activity addActivityWithOneImage(@RequestPart("activity") Activity activity,
            @RequestPart("imageFile") MultipartFile[] images) {
        return activityService.addActivityWithOneImage(activity, images);
    }

    // Add Images to Activity
    // --------------------------------------------------------------------------------------------------------------------------

    @PutMapping(value = { "/add-images-to-activity" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Activity addImagesToActivity(@RequestPart("id") Long actId,
            @RequestPart("imageFile") MultipartFile[] images) {
        return activityService.addImagesToActivity(actId, images);
    }

    // Update Activity
    // ----------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = { "/update-activity" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Activity updateActivity(@RequestPart("activity") Activity activity,
            @RequestPart("imageFile") MultipartFile[] images) {
        return activityService.updateActivity(activity, images);
    }

    // Delete Activity image
    // ----------------------------------------------------------------------------------------------------------------------
    @DeleteMapping(value = { "/delete-activity-image/{actId}/{imageName}" })
    @ResponseBody
    public Activity deleteActivityImage(@PathVariable Long actId, @PathVariable String imageName) {
        return activityService.deleteActivityImage(actId, imageName);
    }

    @PutMapping("/add-coach-to-activity/{coachId}/{activityId}")
    public void addCoachToActivity(@PathVariable Long activityId, @PathVariable Long coachId) {
        activityService.addCoachToActivity(activityId, coachId);
    }

}
