package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository <Offer, Long> { }
