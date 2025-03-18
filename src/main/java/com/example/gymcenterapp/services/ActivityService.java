package com.example.gymcenterapp.services;

import com.example.gymcenterapp.controllers.ActivityController;
import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Coach;
import com.example.gymcenterapp.entities.ImageModel;
import com.example.gymcenterapp.entities.Offer;
import com.example.gymcenterapp.entities.Session;
import com.example.gymcenterapp.entities.Subscription;
import com.example.gymcenterapp.interfaces.IActivityService;
import com.example.gymcenterapp.repositories.ActivityRepository;
import com.example.gymcenterapp.repositories.CoachRepository;
import com.example.gymcenterapp.repositories.ImageModelRepository;
import com.example.gymcenterapp.repositories.SessionRepository;
import com.example.gymcenterapp.repositories.SubscriptionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ActivityService implements IActivityService {

    @Value("${image.storage.path}")
    private String directory;

    private final ActivityRepository activityRepository;
    private final ImageModelRepository imageModelRepository;
    private final ImageModelService imageModelService;
    private final CoachRepository coachRepository;
    private final CoachService coachService;
    private final SubscriptionRepository subscriptionRepository;
    private final SessionRepository sessionRepository;
    // private final ActivityController activityController;

    /*
     * ActivityService(ActivityController activityController) {
     * this.activityController = activityController;
     * }
     */

    @Override
    public Activity addActivityWithOneImage(Activity activity, MultipartFile[] file) {
        String[] imageType = Objects.requireNonNull(file[0].getContentType()).split("/");
        String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
        String filePath = directory + "/activities/" + uniqueName;

        try {
            ImageModel imageModel = new ImageModel();
            imageModel.setImageName(uniqueName);
            imageModel.setImageType(file[0].getContentType());
            imageModel.setImageSize(file[0].getSize());
            imageModel.setImageUrl(filePath);

            HashSet<ImageModel> images = new HashSet<>();
            images.add(imageModel);

            activity.setActImage(uniqueName);
            activity.setActivityImages(images);

            file[0].transferTo(new File(filePath));

            return activityRepository.save(activity);
        } catch (Exception e) {
            System.out.println("Error in create activity: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Activity addActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public List<Activity> retrieveAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Activity retrieveActivity(Long id) {
        return activityRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteActivity(Long id) {
        Activity activity = activityRepository.findById(id).orElse(null);

        if (activity != null) {
            List<Subscription> subscriptions = new ArrayList<>();
            List<Session> sessions = new ArrayList<>();

            activity.getActivityImages().forEach((image) -> {
                imageModelService.removeFile(directory + "/activities", image.getImageName());
            });

            activity.getActSubscriptions().forEach((subscription) -> {
                Subscription subscriptionObject = subscription;
                subscriptionObject.setMember(null);
                subscriptions.add(subscriptionObject);
            });

            activity.getActSessions().forEach((session) -> {
                Session sessionObject = session;
                sessionObject.setSessionCoach(null);
                sessions.add(sessionObject);
            });

            subscriptions.forEach((subscription) -> subscriptionRepository.save(subscription));
            sessions.forEach((session) -> sessionRepository.save(session));
            activity.getActCoaches()
                    .forEach((coach) -> coachService.deleteCoachActivities(coach.getUserId(), activity.getActId()));

            activityRepository.deleteById(id);
        }
    }

    @Override
    public Activity updateActivity(Long id, Activity activity) {
        Activity existingActivity = activityRepository.findById(id).orElse(null);

        if (existingActivity != null) {
            existingActivity.setActName(activity.getActName());
            existingActivity.setActDescription(activity.getActDescription());
            existingActivity.setCategory(activity.getCategory());
            return activityRepository.save(existingActivity);
        } else {
            System.out.println("Cannot find activity(update method)!!!");
            return null;
        }

    }

    @Override
    public List<Activity> getCategoryActivities(Long categoryId) {
        return activityRepository.getCategoryActivities(categoryId);
    }

    @Override
    public Activity addImagesToActivity(Long actId, MultipartFile[] files) {
        try {
            Activity activity = activityRepository.findById(actId).orElse(null);

            assert activity != null;
            Set<ImageModel> images = imageModelService.prepareFiles(files, activity.getActivityImages(),
                    directory + "/activities/");

            activity.setActivityImages(images);

            return activityRepository.save(activity);
        } catch (Exception e) {
            System.out.println("Error in add Images To Activity: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Activity deleteActivityImage(Long actId, String imageName) {
        Activity activity = activityRepository.findById(actId).orElse(null);
        ImageModel imageModel = imageModelRepository.findByName(imageName);

        if (activity != null && imageModel != null) {
            activity.getActivityImages().remove(imageModel);
            imageModelService.removeFile(directory + "/activities/", imageName);
            imageModelRepository.delete(imageModel);
            return activityRepository.save(activity);
        } else {
            System.out.println("activity or image is null");
            return null;
        }
    }

    @Override
    public Activity updateActivity(Activity activity, MultipartFile[] file) {
        Activity existingActivity = activityRepository.findById(activity.getActId()).orElse(null);

        if (existingActivity != null) {
            existingActivity.setActName(activity.getActName());
            existingActivity.setActDescription(activity.getActDescription());

            String[] imageType = Objects.requireNonNull(file[0].getContentType()).split("/");
            String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
            String filePath = directory + "/activities/" + uniqueName;

            try {
                ImageModel imageModel = new ImageModel();
                imageModel.setImageName(uniqueName);
                imageModel.setImageType(file[0].getContentType());
                imageModel.setImageSize(file[0].getSize());
                imageModel.setImageUrl(filePath);

                Set<ImageModel> images = existingActivity.getActivityImages();
                images.add(imageModel);

                file[0].transferTo(new File(filePath));

                ImageModel existingImageModel = imageModelService.findImageByName(existingActivity.getActImage());

                images.remove(existingImageModel);
                imageModelRepository.delete(existingImageModel);
                imageModelService.removeFile(directory + "/activities/", existingActivity.getActImage());

                existingActivity.setActImage(uniqueName);
                existingActivity.setActivityImages(images);

                return activityRepository.save(existingActivity);
            } catch (Exception e) {
                System.out.println("Error in update activity with image: " + e.getMessage());
                return null;
            }

        } else {
            return null;
        }
    }

    @Override
    public void addCoachToActivity(Long activityId, Long coachId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        Coach coach = coachRepository.findById(coachId).orElse(null);
        if ((activity != null) && (coach != null)) {
            Set<Activity> setActivity = coach.getCoachSpecialities();
            Set<Coach> setCoach = activity.getActCoaches();

            setActivity.add(activity);
            setCoach.add(coach);

            activity.setActCoaches(setCoach);
            coach.setCoachSpecialities(setActivity);

            activityRepository.save(activity);
            coachRepository.save(coach);
            System.out.println("coach added successfully !");
        } else {
            System.out.println("coach or activity is null in addCoachToActivity");
        }
    }

    @Override
    public Set<Offer> retrieveActivityOffers(Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        Set<Offer> offers = new HashSet<>();
        if (activity != null) {
            offers = activity.getActivityOffers();
        }

        return offers;
    }

}
