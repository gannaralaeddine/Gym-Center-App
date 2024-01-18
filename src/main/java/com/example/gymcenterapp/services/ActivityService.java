package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.ImageModel;
import com.example.gymcenterapp.interfaces.IActivityService;
import com.example.gymcenterapp.repositories.ActivityRepository;
import com.example.gymcenterapp.repositories.ImageModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
@AllArgsConstructor

public class ActivityService implements IActivityService
{
//    final String directory = "C:\\Users\\ganna\\IdeaProjects\\Gym-Center-App\\src\\main\\resources\\static\\activities\\";
    final String directory = "C:\\Users\\awadi\\Desktop\\Projet PFE\\back\\Gym-Center-App\\src\\main\\resources\\static\\activities\\";


    ActivityRepository activityRepository;
    ImageModelRepository imageModelRepository;
    ImageModelService imageModelService;

    @Override
    public Activity addActivityWithOneImage(Activity activity, MultipartFile[] file)
    {
        String[] imageType = file[0].getContentType().split("/");
        String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
        String filePath = directory + uniqueName;

        try
        {
            ImageModel imageModel = new  ImageModel();
            imageModel.setImageName( uniqueName );
            imageModel.setImageType( file[0].getContentType() );
            imageModel.setImageSize( file[0].getSize() );
            imageModel.setImageUrl( filePath );

            HashSet<ImageModel> images = new HashSet<>();
            images.add(imageModel);

            activity.setActImage(uniqueName);
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
            existingActivity.setCategory(activity.getCategory());
            return activityRepository.save(existingActivity);
        }
        else
        {
            System.out.println("Cannot find activity(update method)!!!");
            return null;
        }


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
            System.out.println("Error in add Images To Activity: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Activity deleteActivityImage(Long actId, String imageName)
    {
        Activity activity = activityRepository.findById(actId).orElse(null);
        ImageModel imageModel = imageModelRepository.findByName(imageName);

        if (activity != null && imageModel != null)
        {
            activity.getActivityImages().remove(imageModel);
            imageModelService.removeFile(directory, imageName);
            imageModelRepository.delete(imageModel);
            return activityRepository.save(activity);
        }
        else
        {
            System.out.println("activity or image is null");
            return null;
        }
    }


    @Override
    public Activity updateActivity( Activity activity, MultipartFile[] file)
    {
        Activity existingActivity = activityRepository.findById(activity.getActId()).orElse(null);

        if (existingActivity != null)
        {
            existingActivity.setActName(activity.getActName());
            existingActivity.setActDescription(activity.getActDescription());

            String[] imageType = file[0].getContentType().split("/");
            String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
            String filePath = directory + uniqueName;

            try
            {
                ImageModel imageModel = new ImageModel();
                imageModel.setImageName( uniqueName );
                imageModel.setImageType(file[0].getContentType());
                imageModel.setImageSize(file[0].getSize());
                imageModel.setImageUrl(filePath);

                Set<ImageModel> images = existingActivity.getActivityImages();
                images.add(imageModel);

                file[0].transferTo(new File(filePath));

                ImageModel existingImageModel = imageModelService.findImageByName(existingActivity.getActImage());

                images.remove(existingImageModel);
                imageModelRepository.delete(existingImageModel);
                imageModelService.removeFile(directory, existingActivity.getActImage());


                existingActivity.setActImage( uniqueName );
                existingActivity.setActivityImages(images);


                return activityRepository.save(existingActivity);
            }
            catch (Exception e)
            {
                System.out.println("Error in update activity with image: " + e.getMessage());
                return null;
            }

        }
        else
        {
            return null;
        }
    }

}
