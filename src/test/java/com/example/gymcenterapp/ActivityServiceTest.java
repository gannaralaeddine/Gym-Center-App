package com.example.gymcenterapp;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.services.ActivityService;
import com.example.gymcenterapp.services.CategoryService;


@SpringBootTest
public class ActivityServiceTest 
{
    @Autowired
    private ActivityService activityService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void addActivity() 
    {
        List<Category> categories = categoryService.retrieveAllCategories();
        assertNotNull(categories);
        Activity activity= activityService.addActivity(new Activity(null, "musculation", "description activité", null, categories.get(0), null, null, null, null, null));
        assertNotNull(activity);
        activity.setCategory(null);
        activityService.deleteActivity(activityService.addActivity(activity).getActId());
    }

    @Test
    public void retrieveAllActivities() { assertNotNull(activityService.retrieveAllActivities()); }

    @Test
    public void retrieveActivity() 
    { 
        List<Category> categories = categoryService.retrieveAllCategories();
        assertNotNull(categories);
        Activity activity= activityService.addActivity(new Activity(null, "musculation", "description activité", null, categories.get(0), null, null, null, null, null));
        assertNotNull(activityService.retrieveActivity(activity.getActId()));
        activity.setCategory(null);
        activityService.deleteActivity(activityService.addActivity(activity).getActId());
    }

    @Test
    public void updateActivity()
    {
        List<Category> categories = categoryService.retrieveAllCategories();
        assertNotNull(categories);
        Activity activity= activityService.addActivity(new Activity(null, "musculation", "description activité", null, categories.get(0), null, null, null, null, null));
        assertNotNull(activity);
        activity = activityService.updateActivity(activity.getActId(), new Activity(null, "body building", "description activité", null, categories.get(0), null, null, null, null, null));
        activity.setCategory(null);
        activityService.deleteActivity(activityService.addActivity(activity).getActId());
    }

    @Test
    public void deleteActivity()
    {
        addActivity();
    }
}
