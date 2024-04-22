package com.example.gymcenterapp.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.gymcenterapp.entities.Option;
import com.example.gymcenterapp.interfaces.IOptionService;
import com.example.gymcenterapp.repositories.OptionRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OptionService implements IOptionService
{
    private OptionRepository optionRepository;

    @Override
    public Option createOption(Option option) 
    {
        return optionRepository.save(option);
    }

    @Override
    public List<Option> retrieveAllOptions() 
    {
        return optionRepository.findAll();
    }

    @Override
    public Option retrieveOption(Long id) 
    {
        return optionRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteOption(Long id) 
    {
        optionRepository.deleteById(id);
    }

    @Override
    public Option updateOption(Long id, Option option) 
    {
        Option existingOption = optionRepository.findById(id).orElse(null);

        if (existingOption != null)
        {
            existingOption.setOptionName(option.getOptionName());
            existingOption.setOptionOffer(option.getOptionOffer());
            return optionRepository.save(existingOption);
        }
        else
        {
            System.out.println("Cannot find option update method !!!");
            return null;
        }
    }

}

