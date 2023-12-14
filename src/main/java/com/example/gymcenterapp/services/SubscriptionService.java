package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.Subscription;
import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.interfaces.ISubscriptionService;
import com.example.gymcenterapp.interfaces.IUserService;
import com.example.gymcenterapp.repositories.SubscriptionRepository;
import com.example.gymcenterapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubscriptionService implements ISubscriptionService
{
    SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription addSubscription(Subscription subscription) { return subscriptionRepository.save(subscription); }

    @Override
    public List<Subscription> retrieveAllSubscriptions() { return subscriptionRepository.findAll(); }

    @Override
    public Subscription retrieveSubscription(Long id) { return subscriptionRepository.findById(id).orElse(null); }

    @Override
    public void deleteSubscription(Long id) { subscriptionRepository.deleteById(id); }

    @Override
    public Subscription updateSubscription(Long id, Subscription subscription)
    {
        Subscription existingSubscription = subscriptionRepository.findById(id).orElse(null);

        if (existingSubscription != null)
        {
            existingSubscription.setSubCategories(subscription.getSubCategories());
            existingSubscription.setSubActivities(subscription.getSubActivities());
            return subscriptionRepository.save(existingSubscription);
        }

        return null;
    }
}
