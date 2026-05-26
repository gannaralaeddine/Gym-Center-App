package com.example.gymcenterapp.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Embeddable
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

    @Column(name = "offer_title")
    private String offerTitle;

    @Column(name = "offer_period")
    private Integer offerPeriod;

    @Column(name = "offer_price")
    private Double offerPrice;

    @ManyToOne
    @JoinColumn(name = "offer_activity")
    private Activity offerActivity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "offer_option", joinColumns = { @JoinColumn(name = "offer_id") }, inverseJoinColumns = {
            @JoinColumn(name = "option_id") })
    private List<Option> offerOption;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    private Subscription subscription;


    @OneToMany(mappedBy = "subscriptionOffer")
    private List<Subscription> subscriptions = new ArrayList<>();
}
