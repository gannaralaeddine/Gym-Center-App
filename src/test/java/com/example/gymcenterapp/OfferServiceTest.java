package com.example.gymcenterapp;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Offer;
import com.example.gymcenterapp.entities.Option;
import com.example.gymcenterapp.services.ActivityService;
import com.example.gymcenterapp.services.OfferService;
import com.example.gymcenterapp.services.OptionService;


@SpringBootTest
class OfferServiceTest
{
    @Autowired
    private ActivityService activityService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private OfferService offerService;

    private Offer buildOffer(String title, Activity activity, List<Option> options) {
        Offer offer = new Offer();
        offer.setOfferTitle(title);
        offer.setOfferPeriod(12);
        offer.setOfferPrice(30.0);
        offer.setOfferActivity(activity);
        offer.setOfferOption(options);
        return offer;
    }

    @Test
    void addOffer()
    {
        List<Activity> activities = activityService.retrieveAllActivities();
        assertNotNull(activities);
        Offer offer = offerService.addOffer(buildOffer("offre", activities.get(0), null));
        assertNotNull(offer);
        offer.setOfferActivity(null);
        offerService.deleteOffer(offerService.addOffer(offer).getOfferId());
    }

    @Test
    void retrieveAllOffers() { assertNotNull(offerService.retrieveAllOffers()); }

    @Test
    void retrieveOffer()
    { 
        List<Activity> activities = activityService.retrieveAllActivities();
        assertNotNull(activities);
        Offer offer = offerService.addOffer(buildOffer("offre", activities.get(0), null));
        assertNotNull(offerService.retrieveOffer(offer.getOfferId()));
        offer.setOfferActivity(null);
        offerService.deleteOffer(offerService.addOffer(offer).getOfferId());
    }

    @Test
    void updateOffer()
    {
        List<Activity> activities = activityService.retrieveAllActivities();
        List<Option> options = optionService.retrieveAllOptions();
        assertNotNull(options);
        assertNotNull(activities);
        Offer offer = offerService.addOffer(buildOffer("offre", activities.get(0), null));
        assertNotNull(offer);
        offer = offerService.updateOffer(offer.getOfferId(), buildOffer("offre 2", activities.get(0), options));
        offer.setOfferActivity(null);
        offer.setOfferOption(null);
        offerService.deleteOffer(offerService.addOffer(offer).getOfferId());
    }

    @Test
    void deleteOffer()
    {
       addOffer();
    }
}
