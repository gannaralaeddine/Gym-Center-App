package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.Subscription;
import com.example.gymcenterapp.enumerated.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


    boolean existsByMemberUserIdAndSubscriptionOfferOfferIdAndStatus(Long memberId, Long offerId, SubscriptionStatus subscriptionStatus);


    @Query( "SELECT s.member.userId FROM Subscription s WHERE s.subscriptionOffer.offerId = :offerId AND s.status = 'ACTIVE' ")
    List<Long> findSubscribedMemberIdsByOffer(Long offerId);
}
