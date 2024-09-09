package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Offer;
import com.example.gymcenterapp.repositories.OfferRepository;
import com.example.gymcenterapp.services.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/offer")
@AllArgsConstructor

public class OfferController
{
    OfferService offerService;
    OfferRepository offerRepository;

    @GetMapping("/retrieve-all-offers")
    @ResponseBody
    public List<Offer> getAllOffers() { return offerService.retrieveAllOffers(); }


    @GetMapping("/retrieve-offer/{offer-id}")
    @ResponseBody
    public Offer retrieveOffer(@PathVariable("offer-id") Long offerId) { return offerService.retrieveOffer(offerId); }

    @PostMapping(value = "/add-offer")
    @ResponseBody
    public Offer addOffer(@RequestBody Offer offer) { return offerService.addOffer(offer); }

    @PutMapping(value = "/update-offer/{offer-id}")
    @ResponseBody
    public Offer updateOffer(@PathVariable("offer-id") Long offerId, @RequestBody Offer offer) { return offerService.updateOffer(offerId,offer); }


    @DeleteMapping(value = "/delete-offer/{offer-id}")
    public void deleteOffer(@PathVariable("offer-id") Long offerId) { offerService.deleteOffer(offerId); }
}
