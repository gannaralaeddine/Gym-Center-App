package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Subscription;

import java.util.List;

public interface ISubscriptionService
{
    Subscription addSubscription(Subscription subscription);

    List<Subscription> retrieveAllSubscriptions();

    Subscription retrieveSubscription(Long id);

    void deleteSubscription(Long id);

    Subscription updateSubscription(Long id, Subscription subscription);

//    void addMemberToSubscription(Long subscriptionId, Long memberId);
}
