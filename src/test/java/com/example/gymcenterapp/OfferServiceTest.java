package com.example.gymcenterapp;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.Offer;
import com.example.gymcenterapp.entities.Option;
import com.example.gymcenterapp.services.ActivityService;
import com.example.gymcenterapp.services.CategoryService;
import com.example.gymcenterapp.services.OfferService;
import com.example.gymcenterapp.services.OptionService;

@SpringBootTest
public class OfferServiceTest {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private OfferService offerService;

    @Test
    public void addOffer() {
        List<Activity> activities = activityService.retrieveAllActivities();
        assertNotNull(activities);
        Offer offer = offerService.addOffer(new Offer(null, "offre", 12, 30.0, activities.get(0), null, null));
        assertNotNull(offer);
        offer.setOfferActivity(null);
        offerService.deleteOffer(offerService.addOffer(offer).getOfferId());
    }

    @Test
    public void retrieveAllOffers() {
        assertNotNull(offerService.retrieveAllOffers());
    }

    @Test
    public void retrieveOffer() {
        List<Activity> activities = activityService.retrieveAllActivities();
        assertNotNull(activities);
        Offer offer = offerService.addOffer(new Offer(null, "offre", 12, 30.0, activities.get(0), null, null));
        assertNotNull(offerService.retrieveOffer(offer.getOfferId()));
        offer.setOfferActivity(null);
        offerService.deleteOffer(offerService.addOffer(offer).getOfferId());
    }

    @Test
    public void updateOffer() {
        List<Activity> activities = activityService.retrieveAllActivities();
        List<Option> options = optionService.retrieveAllOptions();
        assertNotNull(options);
        assertNotNull(activities);
        Offer offer = offerService.addOffer(new Offer(null, "offre", 12, 30.0, activities.get(0), null, null));
        assertNotNull(offer);
        offer = offerService.updateOffer(offer.getOfferId(),
                new Offer(null, "offre 2", 12, 30.0, activities.get(0), options, null));
        offer.setOfferActivity(null);
        offer.setOfferOption(null);
        offerService.deleteOffer(offerService.addOffer(offer).getOfferId());
    }

    @Test
    public void deleteOffer() {
        addOffer();
    }
}
