package com.example.gymcenterapp.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.gymcenterapp.entities.Offer;
import com.example.gymcenterapp.entities.Option;
import com.example.gymcenterapp.interfaces.IOfferService;
import com.example.gymcenterapp.repositories.OfferRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OfferService implements IOfferService
{
    private OfferRepository offerRepository;
    private OptionService optionService;
    
    @Override
    public Offer addOffer(Offer offer) 
    {
        List <Option> options = new ArrayList<>();
        for (Option option : offer.getOfferOption()) 
        { 
            options.add(optionService.retrieveOption(option.getOptionId())); 
        }
        offer.setOfferOption(options);
        return offerRepository.save(offer); 
    }

    @Override
    public List<Offer> retrieveAllOffers() { return offerRepository.findAll(); }

    @Override
    public Offer retrieveOffer(Long id) { return offerRepository.findById(id).orElse(null); }

    @Override
    public void deleteOffer(Long id) { offerRepository.deleteById(id); }

    @Override
    public Offer updateOffer(Long id, Offer offer) 
    {
        Offer existingOffer = offerRepository.findById(id).orElse(null);
        List <Option> options = new ArrayList<>();

        if (existingOffer != null)
        {
            existingOffer.setOfferTitle(offer.getOfferTitle());
            existingOffer.setOfferPeriod(offer.getOfferPeriod());
            existingOffer.setOfferPrice(offer.getOfferPrice());
            existingOffer.setOfferActivity(offer.getOfferActivity());
            for (Option option : offer.getOfferOption()) 
            { 
                options.add(optionService.retrieveOption(option.getOptionId())); 
            }
            existingOffer.getOfferOption().clear();
            existingOffer.setOfferOption(options);
            return offerRepository.save(existingOffer);
        }
        else
        {
            System.out.println("Cannot find activity(update method)!!!");
            return null;
        }
    }

}
