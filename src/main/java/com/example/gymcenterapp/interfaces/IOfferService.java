package com.example.gymcenterapp.interfaces;

import java.util.List;
import com.example.gymcenterapp.entities.Offer;

public interface IOfferService 
{
    Offer addOffer(Offer offer);

    List<Offer> retrieveAllOffers();

    Offer retrieveOffer(Long id);
    
    void deleteOffer(Long id);

    Offer updateOffer(Long id, Offer offer);
}
