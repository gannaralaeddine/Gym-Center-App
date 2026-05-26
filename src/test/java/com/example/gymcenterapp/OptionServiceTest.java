package com.example.gymcenterapp;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gymcenterapp.entities.Option;
import com.example.gymcenterapp.services.OptionService;


@SpringBootTest
class OptionServiceTest
{
    @Autowired
    private OptionService optionService;

    @Test
    void addOption()
    {
        Option option = optionService.createOption(new Option(null, "option 1", null));
        assertNotNull(option);
        optionService.deleteOption(option.getOptionId());    
    }

    @Test
    void retrieveAllOptions() { assertNotNull(optionService.retrieveAllOptions()); }

    @Test
    void retrieveOption()
    { 
        Option option = optionService.createOption(new Option(null, "oprion 1", null));
        assertNotNull(optionService.retrieveOption(option.getOptionId()));
        optionService.deleteOption(option.getOptionId());
    }

    @Test
    void updateOption()
    {
        Option option = optionService.createOption(new Option(null, "option 1", null));
        assertNotNull(option);
        option = optionService.updateOption(option.getOptionId(),new Option(null, "option 2", null));
        assertNotNull(option);
        optionService.deleteOption(option.getOptionId());
    }

    @Test
    void deleteOption()
    {
       addOption(); 
    }
}
