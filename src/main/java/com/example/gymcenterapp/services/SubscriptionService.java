package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.entities.Subscription;
import com.example.gymcenterapp.interfaces.ISubscriptionService;
import com.example.gymcenterapp.repositories.ActivityRepository;
import com.example.gymcenterapp.repositories.MemberRepository;
import com.example.gymcenterapp.repositories.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class SubscriptionService implements ISubscriptionService
{
    SubscriptionRepository subscriptionRepository;
    MemberRepository memberRepository;
    ActivityRepository activityRepository;

    @Override
    public Subscription addSubscription(Subscription subscription, Long memberId) 
    { 
        Member member = memberRepository.findById(memberId).orElse(null);

        if (member != null)
        {
            subscription.setMember(member);
        }

        return subscriptionRepository.save(subscription); 
    }

    @Override
    public List<Subscription> retrieveAllSubscriptions() { return subscriptionRepository.findAll(); }

    @Override
    public Subscription retrieveSubscription(Long id) { return subscriptionRepository.findById(id).orElse(null); }

    @Override
    public void deleteSubscription(Long id) { subscriptionRepository.deleteById(id); }

    @Override
    public Subscription updateSubscription(Long id,Long memberId ,Subscription subscription)
    {
        Subscription existingSubscription = subscriptionRepository.findById(id).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);

        if (existingSubscription != null && member != null)
        {
            existingSubscription.setSubscriptionPrice(subscription.getSubscriptionPrice());
            existingSubscription.setSubscriptionStartDate(subscription.getSubscriptionStartDate());
            existingSubscription.setSubscriptionEndDate(subscription.getSubscriptionEndDate());
            existingSubscription.setSubscriptionActivity(subscription.getSubscriptionActivity());
            existingSubscription.setMember(member);
            return subscriptionRepository.save(existingSubscription);
        }
        else if (existingSubscription != null)
        {
            existingSubscription.setSubscriptionPrice(subscription.getSubscriptionPrice());
            existingSubscription.setSubscriptionStartDate(subscription.getSubscriptionStartDate());
            existingSubscription.setSubscriptionEndDate(subscription.getSubscriptionEndDate());
            existingSubscription.setSubscriptionActivity(subscription.getSubscriptionActivity());    
            return subscriptionRepository.save(existingSubscription);        
        }

        return null;
    }

    public Set<Subscription> retrieveActivitySubscriptions(Long activityId)
    {
        Activity activity = activityRepository.findById(activityId).orElse(null);

        if (activity != null)
        {
            return activity.getActSubscriptions();
        }
        
        return null;
    }
}
