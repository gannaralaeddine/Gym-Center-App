package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Subscription;
import java.util.List;

public interface ISubscriptionService
{
    Subscription addSubscription(Subscription subscription, Long memberId);

    List<Subscription> retrieveAllSubscriptions();

    Subscription retrieveSubscription(Long id);

    void deleteSubscription(Long id);

    Subscription updateSubscription(Long id,Long memberId,Subscription subscription);
}
