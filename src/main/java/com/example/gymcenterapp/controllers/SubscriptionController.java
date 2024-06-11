package com.example.gymcenterapp.controllers;
import com.example.gymcenterapp.entities.Subscription;
import com.example.gymcenterapp.services.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/subscription")
@AllArgsConstructor

public class SubscriptionController
{
    SubscriptionService subscriptionService;

    @PostMapping(value = "/create-subscription/{member-id}")
    @ResponseBody
    public Subscription addSubscription(@RequestBody Subscription subscription, @PathVariable("member-id") Long memberId) 
    { 
        return subscriptionService.addSubscription(subscription, memberId); 
    }

    @PutMapping(value= "/update-subscription/{subscription-id}/{member-id}")
    @ResponseBody
    public Subscription updateSubscription(@PathVariable("subscription-id") Long idSubscription, @PathVariable("member-id") Long memberId, @RequestBody Subscription subscription) { return subscriptionService.updateSubscription(idSubscription,memberId,subscription); }

    @DeleteMapping(value = "/delete-subscription/{id}")
    public void deleteSubscription(@PathVariable("id") Long idSubscription) { subscriptionService.deleteSubscription(idSubscription); }

    @GetMapping("/retrieve-all-subscriptions")
    @ResponseBody
    public List<Subscription> getAllSubscriptions() { return subscriptionService.retrieveAllSubscriptions(); }


    @GetMapping("/retrieve-subscription/{id}")
    @ResponseBody
    public Subscription retrieveSubscription(@PathVariable("id") Long idSubscription) { return subscriptionService.retrieveSubscription(idSubscription); }

    @GetMapping("/retrieve-activity-subscriptions/{activity-id}")
    @ResponseBody
    public Set<Subscription> retrieveActivitySubscriptions(@PathVariable("activity-id") Long activityId) { return subscriptionService.retrieveActivitySubscriptions(activityId); }

//    @PutMapping("/assign-member-to-subscription/{subscriptionId}/{memberId}")
//    public void addMemberToSubscription(@PathVariable Long subscriptionId,@PathVariable Long memberId)
//    {
//        subscriptionService.addMemberToSubscription(subscriptionId, memberId);
//    }
}
