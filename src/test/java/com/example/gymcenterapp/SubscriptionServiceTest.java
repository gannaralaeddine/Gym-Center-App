package com.example.gymcenterapp;

import static org.junit.Assert.assertNotNull;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.entities.Subscription;
import com.example.gymcenterapp.services.ActivityService;
import com.example.gymcenterapp.services.CategoryService;
import com.example.gymcenterapp.services.MemberService;
import com.example.gymcenterapp.services.SubscriptionService;


@SpringBootTest
public class SubscriptionServiceTest 
{
    @Autowired
    private ActivityService activityService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Test
    public void addSubscription() 
    {
        List<Member> members = memberService.retrieveAllMembers();
        List<Activity> activities = activityService.retrieveAllActivities();
        assertNotNull(members);
        assertNotNull(activities);
        Subscription subscription = subscriptionService.addSubscription(new Subscription(null, 0, new Date(), null, activities.get(0), members.get(0)),  members.get(0).getUserId());
        assertNotNull(subscription);
        Long memberId = subscription.getSubscriptionId();
        subscription.setMember(null);
        subscription.setSubscriptionActivity(null);;
        subscriptionService.deleteSubscription(subscriptionService.addSubscription(subscription, memberId).getSubscriptionId());
    }

    @Test
    public void retrieveAllSubscriptions() { assertNotNull(subscriptionService.retrieveAllSubscriptions()); }

     @Test
    public void retrieveSubscription() 
    { 
        List<Member> members = memberService.retrieveAllMembers();
        List<Activity> activities = activityService.retrieveAllActivities();
        assertNotNull(members);
        assertNotNull(activities);
        Subscription subscription = subscriptionService.addSubscription(new Subscription(null, 0, new Date(), null, activities.get(0), members.get(0)),  members.get(0).getUserId());
        assertNotNull(subscriptionService.retrieveSubscription(subscription.getSubscriptionId()));
        Long memberId = subscription.getSubscriptionId();
        subscription.setMember(null);
        subscription.setSubscriptionActivity(null);;
        subscriptionService.deleteSubscription(subscriptionService.addSubscription(subscription, memberId).getSubscriptionId());
    }

    @Test
    public void updateSubscription()
    {
        List<Member> members = memberService.retrieveAllMembers();
        List<Activity> activities = activityService.retrieveAllActivities();
        assertNotNull(members);
        assertNotNull(activities);
        Subscription subscription = subscriptionService.addSubscription(new Subscription(null, 0, new Date(), null, activities.get(0), members.get(0)),  members.get(0).getUserId());
        assertNotNull(subscription);
        Long memberId = subscription.getSubscriptionId();
        subscription = subscriptionService.updateSubscription(subscription.getSubscriptionId(), memberId, new Subscription(null, 100, new Date(), null, activities.get(0), members.get(1)));
        subscription.setMember(null);
        subscription.setSubscriptionActivity(null);;
        subscriptionService.deleteSubscription(subscriptionService.addSubscription(subscription, memberId).getSubscriptionId());
    }

    @Test
    public void deleteSubscription()
    {
        addSubscription();
    }
}
