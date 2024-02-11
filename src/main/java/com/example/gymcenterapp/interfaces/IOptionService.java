package com.example.gymcenterapp.interfaces;

import java.util.List;
import com.example.gymcenterapp.entities.Option;

public interface IOptionService 
{
    Option createOption(Option option);

    List<Option> retrieveAllOptions();

    Option retrieveOption(Long id);
    
    void deleteOption(Long id);

    Option updateOption(Long id, Option option);
}
