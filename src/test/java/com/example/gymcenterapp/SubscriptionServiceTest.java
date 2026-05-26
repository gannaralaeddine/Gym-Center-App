package com.example.gymcenterapp;

import java.time.LocalDateTime;
import java.util.List;

import com.example.gymcenterapp.enumerated.SubscriptionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.entities.Subscription;
import com.example.gymcenterapp.services.ActivityService;
import com.example.gymcenterapp.services.MemberService;
import com.example.gymcenterapp.services.SubscriptionService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubscriptionServiceTest {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Test
    void addSubscription() {

        List<Member> members = memberService.retrieveAllMembers();
        List<Activity> activities = activityService.retrieveAllActivities();

        assertFalse(members.isEmpty());
        assertFalse(activities.isEmpty());

        Member member = members.get(0);
        Activity activity = activities.get(0);

        Subscription subscription = new Subscription();
        subscription.setSubscriptionPrice(0.0);
        subscription.setSubscriptionStartDate(LocalDateTime.now());
        subscription.setSubscriptionEndDate(null);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setSubscriptionActivity(activity);
        subscription.setMember(member);

        Subscription saved =
                subscriptionService.addSubscription(subscription, member.getUserId());

        assertNotNull(saved);
        assertNotNull(saved.getSubscriptionId());

        subscriptionService.deleteSubscription(saved.getSubscriptionId());
    }

    @Test
    void retrieveAllSubscriptions() {
        List<Subscription> list = subscriptionService.retrieveAllSubscriptions();
        assertNotNull(list);
    }

    @Test
    void retrieveSubscription() {

        Member member = memberService.retrieveAllMembers().get(0);
        Activity activity = activityService.retrieveAllActivities().get(0);

        Subscription subscription = new Subscription();
        subscription.setSubscriptionPrice(0.0);
        subscription.setSubscriptionStartDate(LocalDateTime.now());
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setSubscriptionActivity(activity);
        subscription.setMember(member);

        Subscription saved =
                subscriptionService.addSubscription(subscription, member.getUserId());

        Subscription found =
                subscriptionService.retrieveSubscription(saved.getSubscriptionId());

        assertNotNull(found);
        assertEquals(saved.getSubscriptionId(), found.getSubscriptionId());

        subscriptionService.deleteSubscription(saved.getSubscriptionId());
    }

    @Test
    void updateSubscription() {

        Member member = memberService.retrieveAllMembers().get(0);
        Activity activity1 = activityService.retrieveAllActivities().get(0);

        Subscription subscription = new Subscription();
        subscription.setSubscriptionPrice(0.0);
        subscription.setSubscriptionStartDate(LocalDateTime.now());
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setSubscriptionActivity(activity1);
        subscription.setMember(member);

        Subscription saved =
                subscriptionService.addSubscription(subscription, member.getUserId());

        Activity activity2 = activityService.retrieveAllActivities().get(1);

        Subscription updateData = new Subscription();
        updateData.setSubscriptionPrice(100.0);
        updateData.setSubscriptionStartDate(LocalDateTime.now());
        updateData.setStatus(SubscriptionStatus.ACTIVE);
        updateData.setSubscriptionActivity(activity2);
        updateData.setMember(member);

        Subscription updated =
                subscriptionService.updateSubscription(
                        saved.getSubscriptionId(),
                        member.getUserId(),
                        updateData
                );

        assertNotNull(updated);
        assertEquals(Double.valueOf(100.0), updated.getSubscriptionPrice());

        subscriptionService.deleteSubscription(updated.getSubscriptionId());
    }

    @Test
    void deleteSubscription() {

        Member member = memberService.retrieveAllMembers().get(0);
        Activity activity = activityService.retrieveAllActivities().get(0);

        Subscription subscription = new Subscription();
        subscription.setSubscriptionPrice(0.0);
        subscription.setSubscriptionStartDate(LocalDateTime.now());
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setSubscriptionActivity(activity);
        subscription.setMember(member);

        Subscription saved =
                subscriptionService.addSubscription(subscription, member.getUserId());

        Long id = saved.getSubscriptionId();

        subscriptionService.deleteSubscription(id);

        assertNull(subscriptionService.retrieveSubscription(id));
    }
}
