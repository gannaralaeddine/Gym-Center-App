package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.repositories.ActivityRepository;
import com.example.gymcenterapp.repositories.CategoryRepository;
import com.example.gymcenterapp.services.ActivityService;
import com.example.gymcenterapp.services.ImageModelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/activity")
@AllArgsConstructor

public class ActivityController
{
    ActivityService activityService;

    CategoryRepository categoryRepository;

    ActivityRepository activityRepository;

    ImageModelService imageModelService;


    @GetMapping("/retrieve-all-activities")
    @ResponseBody
//    @RolesAllowed({ "ROLE_COACH", "ROLE_USER" })
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

    @GetMapping(value = "/get-category-activities/{categoryId}")
    @ResponseBody
    public List<Activity> getCategoryActivities(@PathVariable Long categoryId) { return activityService.getCategoryActivities(categoryId); }



    // Get category image
//--------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/get-image/{image-name}")
    public ResponseEntity<?> getImageByName(@PathVariable("image-name") String imageName) throws IOException
    {
        byte[] imageData = imageModelService.getImage(imageName);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }


// Add Category with one image
//--------------------------------------------------------------------------------------------------------------------------

    @PostMapping(value = { "/create-activity" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Activity addActivityWithOneImage(@RequestPart("activity") Activity activity,
                                            @RequestPart("imageFile") MultipartFile[] images)
    {
        return activityService.addActivityWithOneImage(activity, images);
    }


// Add Images to Activity
//--------------------------------------------------------------------------------------------------------------------------

    @PutMapping(value = { "/add-images-to-activity" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Activity addImagesToActivity(@RequestPart("activity") Activity activity,
                                        @RequestPart("imageFile") MultipartFile[] images)
    {
        return activityService.addImagesToActivity(activity.getActId(), images);
    }


// Update Category
//----------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = { "/update-activity" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Activity updateCategory(@RequestPart("activity") Activity activity,
                                   @RequestPart("imageFile") MultipartFile[] images)
    {
        return activityService.updateActivity(activity, images);
    }
}
