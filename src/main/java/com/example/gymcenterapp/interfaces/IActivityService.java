package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Activity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


public interface IActivityService
{
    Activity addActivityWithOneImage(Activity activity, MultipartFile[] file);

    Activity addImagesToActivity( Long actId, MultipartFile[] files );

    Activity addActivity(Activity activity);

    List<Activity> retrieveAllActivities();

    Activity retrieveActivity(Long id);
    
    void deleteActivity(Long id);

    Activity updateActivity(Long id, Activity activity);

    Activity updateActivity( Activity activity, MultipartFile[] file);

    List<Activity> getCategoryActivities(Long categoryId);

    Activity deleteActivityImage(Long actId, String imageName);

    void addCoachToActivity(Long activityId, Long coachId);
}
