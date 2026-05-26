package com.example.gymcenterapp.services;

import com.example.gymcenterapp.email.service.EmailService;
import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.entities.Offer;
import com.example.gymcenterapp.entities.Subscription;
import com.example.gymcenterapp.enumerated.SubscriptionStatus;
import com.example.gymcenterapp.interfaces.ISubscriptionService;
import com.example.gymcenterapp.repositories.ActivityRepository;
import com.example.gymcenterapp.repositories.MemberRepository;
import com.example.gymcenterapp.repositories.OfferRepository;
import com.example.gymcenterapp.repositories.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class SubscriptionService implements ISubscriptionService {
    SubscriptionRepository subscriptionRepository;
    MemberRepository memberRepository;
    OfferRepository offerRepository;
    ActivityRepository activityRepository;
    EmailService emailService;


    public List<Member> getAvailableMembersForOffer(Long offerId) {

        List<Long> subscribedMemberIds =
                subscriptionRepository.findSubscribedMemberIdsByOffer(offerId);

        // if nobody subscribed yet
        if (subscribedMemberIds.isEmpty()) {
            return memberRepository.findAll();
        }

        return memberRepository.findByUserIdNotIn(subscribedMemberIds);
    }


    @Override
    public Subscription addSubscription(Subscription subscription, Long memberId) {
        return null;
    }

    @Override
    public Subscription createSubscription(Long memberId, Long offerId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new RuntimeException("Offer not found"));

        // RULE: only one ACTIVE subscription per offer per member
        boolean alreadySubscribed = subscriptionRepository.existsByMemberUserIdAndSubscriptionOfferOfferIdAndStatus( memberId,  offerId,  SubscriptionStatus.ACTIVE );

        if (alreadySubscribed) {
            throw new RuntimeException(
                    "Member already has an active subscription for this offer"
            );
        }

        Subscription subscription = new Subscription();

        subscription.setSubscriptionPrice(offer.getOfferPrice());
        subscription.setSubscriptionActivity(offer.getOfferActivity());
        subscription.setMember(member);
        subscription.setSubscriptionOffer(offer);
        LocalDateTime now = LocalDateTime.now();
        subscription.setSubscriptionStartDate(now);
        subscription.setSubscriptionEndDate(now.plusMonths(offer.getOfferPeriod()));

        subscription.setStatus(SubscriptionStatus.ACTIVE);

        // snapshot price (important for history)
        subscription.setSubscriptionPrice(offer.getOfferPrice());

        emailService.sendConfirmationSubscriptionEmail(subscription);

        return subscriptionRepository.save(subscription);
    }




//    @Override
//    public Subscription addSubscription(Subscription subscription, Long memberId) {
//        Member member = memberRepository.findById(memberId).orElse(null);
//        System.out.println(subscription.getSubscriptionOffer().getOfferTitle());
//        System.out.println(subscription.getSubscriptionOffer().getOfferActivity().getActName());
//
//        if (member != null) {
//            subscription.setMember(member);
//            subscription.getSubscriptionOffer().getMembers().add(member);
//            if (!member.getUserIsSubscribed()) {
//                member.setUserIsSubscribed(true);
//            }
//        }
//        emailService.sendConfirmationSubscriptionEmail(subscription);
//        return subscriptionRepository.save(subscription);
//    }

    @Override
    public List<Subscription> retrieveAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription retrieveSubscription(Long id) {
        return subscriptionRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id).orElse(null);

        if (subscription != null) {
            Member member = subscription.getMember();
            emailService.sendCancelSubscriptionEmail(subscription);

            subscription.setMember(null);
            subscription.setSubscriptionActivity(null);
            subscriptionRepository.deleteById(subscriptionRepository.save(subscription).getSubscriptionId());

            if (member != null) {
                if (member.getMemberSubscriptions().isEmpty()) {
                    member.setUserIsSubscribed(false);
                    memberRepository.save(member);
                }
            }
        }

    }

    @Override
    public Subscription updateSubscription(Long id, Long memberId, Subscription subscription) {
        Subscription existingSubscription = subscriptionRepository.findById(id).orElse(null);
        Member newMember = memberRepository.findById(memberId).orElse(null);

        if (existingSubscription != null && newMember != null) {
            Member oldMember = existingSubscription.getMember();
            if ((oldMember.getMemberSubscriptions().remove(existingSubscription))
                    && (oldMember.getMemberSubscriptions().isEmpty())) {
                oldMember.setUserIsSubscribed(false);
                memberRepository.save(oldMember);
            }

            existingSubscription.setSubscriptionPrice(subscription.getSubscriptionPrice());
            existingSubscription.setSubscriptionStartDate(subscription.getSubscriptionStartDate());
            existingSubscription.setSubscriptionEndDate(subscription.getSubscriptionEndDate());
            existingSubscription.setSubscriptionActivity(subscription.getSubscriptionActivity());
            existingSubscription.setSubscriptionOffer(subscription.getSubscriptionOffer());
            newMember.setUserIsSubscribed(true);
            existingSubscription.setMember(newMember);

            return subscriptionRepository.save(existingSubscription);
        } else if (existingSubscription != null) {
            existingSubscription.setSubscriptionPrice(subscription.getSubscriptionPrice());
            existingSubscription.setSubscriptionStartDate(subscription.getSubscriptionStartDate());
            existingSubscription.setSubscriptionEndDate(subscription.getSubscriptionEndDate());
            existingSubscription.setSubscriptionActivity(subscription.getSubscriptionActivity());
            existingSubscription.setSubscriptionOffer(subscription.getSubscriptionOffer());

            return subscriptionRepository.save(existingSubscription);
        }

        return null;
    }

    public Set<Subscription> retrieveActivitySubscriptions(Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);

        if (activity != null) {
            return activity.getActSubscriptions();
        }

        return null;
    }
}
