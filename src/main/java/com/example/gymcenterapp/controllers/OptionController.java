package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Option;
import com.example.gymcenterapp.repositories.OptionRepository;
import com.example.gymcenterapp.services.OptionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/option")
@AllArgsConstructor
public class OptionController
{
    OptionService optionService;
    OptionRepository OptionRepository;

    @GetMapping("/retrieve-all-options")
    @ResponseBody
    public List<Option> getAllOptions() { return optionService.retrieveAllOptions(); }


    @GetMapping("/retrieve-option/{option-id}")
    @ResponseBody
    public Option retrieveOption(@PathVariable("option-id") Long optionId) { return optionService.retrieveOption(optionId); }

    @PostMapping(value = "/create-option")
    @ResponseBody
    public Option createOption(@RequestBody Option option) { return optionService.createOption(option); }

    @PutMapping(value = "/update-option/{option-id}")
    @ResponseBody
    public Option updateOption(@PathVariable("option-id") Long optionId, @RequestBody Option option) { return optionService.updateOption(optionId,option); }


    @DeleteMapping(value = "/delete-option/{option-id}")
    public void deleteOption(@PathVariable("option-id") Long optionId) { optionService.deleteOption(optionId); }
}
