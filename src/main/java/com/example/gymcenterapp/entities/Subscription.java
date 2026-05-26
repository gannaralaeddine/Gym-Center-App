package com.example.gymcenterapp.entities;

import com.example.gymcenterapp.enumerated.SubscriptionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Embeddable
public class Subscription implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "subscription_id")
    private Long subscriptionId;

    @JoinColumn(name = "subscription_price")
    private Double subscriptionPrice;

    @JoinColumn(name = "subscription_start_date")
    private LocalDateTime subscriptionStartDate;

    @JoinColumn(name = "subscription_end_date")
    private LocalDateTime subscriptionEndDate;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "actId")
    private Activity subscriptionActivity;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    @JsonIgnore
    private Offer subscriptionOffer;
}
