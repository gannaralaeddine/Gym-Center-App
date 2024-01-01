package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.ImageModel;
import com.example.gymcenterapp.interfaces.IActivityService;
import com.example.gymcenterapp.repositories.ActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
@AllArgsConstructor

public class ActivityService implements IActivityService
{
    final String directory = "C:\\Users\\ganna\\IdeaProjects\\Gym-Center-App\\src\\main\\resources\\static\\activities\\";

    ActivityRepository activityRepository;

    ImageModelService imageModelService;

    @Override
    public Activity addActivityWithOneImage(Activity activity, MultipartFile[] file)
    {
        String filePath = directory+file[0].getOriginalFilename();

        try
        {
            ImageModel imageModel = new  ImageModel();
            imageModel.setImageName( file[0].getOriginalFilename() );
            imageModel.setImageType( file[0].getContentType() );
            imageModel.setImageSize( file[0].getSize() );
            imageModel.setImageUrl( filePath );

            HashSet<ImageModel> images = new HashSet<>();
            images.add(imageModel);

            activity.setActImage(file[0].getOriginalFilename());
            activity.setActivityImages(images);

            file[0].transferTo(new File(filePath));

            return activityRepository.save(activity);
        }
        catch (Exception e)
        {
            System.out.println("Error in create activity: " + e.getMessage());
            return null;
        }
    }



    @Override
    public Activity addActivity(Activity activity) { return activityRepository.save(activity); }

    @Override
    public List<Activity> retrieveAllActivities() { return activityRepository.findAll(); }

    @Override
    public Activity retrieveActivity(Long id) { return activityRepository.findById(id).orElse(null); }

    @Override
    public void deleteActivity(Long id) { activityRepository.deleteById(id);}

    @Override
    public Activity updateActivity(Long id, Activity activity)
    {
        Activity existingActivity = activityRepository.findById(id).orElse(null);

        if (existingActivity != null)
        {
            existingActivity.setActName(activity.getActName());
            existingActivity.setActDescription(activity.getActDescription());
            existingActivity.setActImage(activity.getActImage());
            existingActivity.setCategory(activity.getCategory());
            return activityRepository.save(existingActivity);
        }

        return null;
    }

    @Override
    public List<Activity> getCategoryActivities(Long categoryId) 
    {
        return activityRepository.getCategoryActivities(categoryId);
    }


    @Override
    public Activity addImagesToActivity(Long actId, MultipartFile[] files)
    {
        try
        {
            Activity activity = activityRepository.findById(actId).orElse(null);

            assert activity != null;
            Set<ImageModel> images =  imageModelService.prepareFiles(files, activity.getActivityImages(), directory);

            activity.setActivityImages(images);

            return activityRepository.save(activity);
        }
        catch (Exception e)
        {
            System.out.println("Error in add Images To Category: " + e.getMessage());
            return null;
        }
    }


}
