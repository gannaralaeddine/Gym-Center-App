package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Subscription;
import com.example.gymcenterapp.services.SubscriptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/subscription")
@AllArgsConstructor

public class SubscriptionController
{
    SubscriptionService subscriptionService;

    @PostMapping(value = "/create-subscription")
    @ResponseBody
    public Subscription addSubscription(@RequestBody Subscription subscription) { return subscriptionService.addSubscription(subscription); }

    @PutMapping(value= "/update-subscription/{id}")
    @ResponseBody
    public Subscription updateSubscription(@PathVariable("id") Long idSubscription, @RequestBody Subscription subscription) { return subscriptionService.updateSubscription(idSubscription,subscription); }

    @DeleteMapping(value = "/delete-subscription/{id}")
    public void deleteSubscription(@PathVariable("id") Long idSubscription) { subscriptionService.deleteSubscription(idSubscription); }

    @GetMapping("/retrieve-all-subscriptions")
    @ResponseBody
    public List<Subscription> getAllSubscriptions() { return subscriptionService.retrieveAllSubscriptions(); }


    @GetMapping("/retrieve-subscription/{id}")
    @ResponseBody
    public Subscription retrieveSubscription(@PathVariable("id") Long idSubscription) { return subscriptionService.retrieveSubscription(idSubscription); }

    @PutMapping("/add-member-to-subscription/{subscriptionId}/{memberId}")
    public void addMemberToSubscription(@PathVariable Long subscriptionId,@PathVariable Long memberId) 
    {
        subscriptionService.addMemberToSubscription(subscriptionId, memberId);
    }
}
