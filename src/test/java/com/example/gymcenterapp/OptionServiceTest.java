package com.example.gymcenterapp;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.Offer;
import com.example.gymcenterapp.entities.Option;
import com.example.gymcenterapp.services.ActivityService;
import com.example.gymcenterapp.services.CategoryService;
import com.example.gymcenterapp.services.OfferService;
import com.example.gymcenterapp.services.OptionService;


@SpringBootTest
public class OptionServiceTest 
{
    @Autowired
    private ActivityService activityService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private OfferService offerService;

    @Test
    public void addOption() 
    {
        Option option = optionService.createOption(new Option(null, "option 1", null));
        assertNotNull(option);
        optionService.deleteOption(option.getOptionId());    
    }

    @Test
    public void retrieveAllOptions() { assertNotNull(optionService.retrieveAllOptions()); }

    @Test
    public void retrieveOption() 
    { 
        Option option = optionService.createOption(new Option(null, "oprion 1", null));
        assertNotNull(optionService.retrieveOption(option.getOptionId()));
        optionService.deleteOption(option.getOptionId());
    }

    @Test
    public void updateOption()
    {
        Option option = optionService.createOption(new Option(null, "option 1", null));
        assertNotNull(option);
        option = optionService.updateOption(option.getOptionId(),new Option(null, "option 2", null));
        assertNotNull(option);
        optionService.deleteOption(option.getOptionId());
    }

    @Test
    public void deleteOption()
    {
       addOption(); 
    }
}
