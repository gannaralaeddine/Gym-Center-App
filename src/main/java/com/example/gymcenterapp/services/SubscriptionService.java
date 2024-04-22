package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.entities.Subscription;
import com.example.gymcenterapp.interfaces.ISubscriptionService;
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
            existingSubscription.setSubscriptionPrice(subscription.getSubscriptionPrice());
            existingSubscription.setSubscriptionStartDate(subscription.getSubscriptionStartDate());
            existingSubscription.setSubscriptionEndDate(subscription.getSubscriptionEndDate());
            existingSubscription.setSubscriptionActivity(subscription.getSubscriptionActivity());
            existingSubscription.setSubscriptionMembers(subscription.getSubscriptionMembers());
            return subscriptionRepository.save(existingSubscription);
        }

        return null;
    }

    @Override
    public void addMemberToSubscription(Long subscriptionId, Long memberId) 
    {
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);

        if ((subscription != null) && (member != null))
        {
            Set<Subscription> setSubscription = member.getMemberSubscriptions();
            Set<Member> setMember = subscription.getSubscriptionMembers();

            setSubscription.add(subscription);
            setMember.add(member);

            subscription.setSubscriptionMembers(setMember);
            member.setMemberSubscriptions(setSubscription);

            subscriptionRepository.save(subscription);
            memberRepository.save(member);
            System.out.println("member added successfully !");
        }
        else
        {
            System.out.println("member or subscription is null in addMemberToSubscription");
        }
    }
}
