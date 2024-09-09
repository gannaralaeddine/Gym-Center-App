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
    MemberService memberService;
    EmailServiceImpl emailService;

    @Override
    public Subscription addSubscription(Subscription subscription, Long memberId) 
    { 
        Member member = memberRepository.findById(memberId).orElse(null);

        if (member != null)
        {
            subscription.setMember(member);
            if (!member.getUserIsSubscribed())
            {
                member.setUserIsSubscribed(true);
            }
        }
        emailService.sendConfirmationSubscriptionEmail(subscription);
        return subscriptionRepository.save(subscription); 
    }

    @Override
    public List<Subscription> retrieveAllSubscriptions() { return subscriptionRepository.findAll(); }

    @Override
    public Subscription retrieveSubscription(Long id) { return subscriptionRepository.findById(id).orElse(null); }


    @Override
    public void deleteSubscription(Long id) 
    { 
        Subscription subscription = subscriptionRepository.findById(id).orElse(null);

        if (subscription != null)
        {
            Member member = subscription.getMember();
            emailService.sendCancelSubscriptionEmail(subscription);

            subscription.setMember(null);
            subscription.setSubscriptionActivity(null);
            subscriptionRepository.deleteById(subscriptionRepository.save(subscription).getSubscriptionId());
           
            if (member != null)
            {
                if (member.getMemberSubscriptions().size() == 0)
                {
                    member.setUserIsSubscribed(false);
                    memberRepository.save(member);
                }
            }
        }

        
    }

    @Override
    public Subscription updateSubscription(Long id,Long memberId ,Subscription subscription)
    {
        Subscription existingSubscription = subscriptionRepository.findById(id).orElse(null);
        Member newMember = memberRepository.findById(memberId).orElse(null);

        if (existingSubscription != null && newMember != null)
        {
            Member oldMember = existingSubscription.getMember();
            if ((oldMember.getMemberSubscriptions().remove(existingSubscription)) && (oldMember.getMemberSubscriptions().size() == 0))
            {
                oldMember.setUserIsSubscribed(false);
                memberRepository.save(oldMember);
            }
           
            existingSubscription.setSubscriptionPrice(subscription.getSubscriptionPrice());
            existingSubscription.setSubscriptionStartDate(subscription.getSubscriptionStartDate());
            existingSubscription.setSubscriptionEndDate(subscription.getSubscriptionEndDate());
            existingSubscription.setSubscriptionActivity(subscription.getSubscriptionActivity());
            newMember.setUserIsSubscribed(true);
            existingSubscription.setMember(newMember);

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
