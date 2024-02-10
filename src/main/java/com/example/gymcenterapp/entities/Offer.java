package com.example.gymcenterapp.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class Offer 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

    @Column(name = "offer_title")
    private String offerTitle;

    @Column(name = "offer_period")
    private Integer offerPeriod;

    @Column(name = "offer_price")
    private Double offerPrice;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "offer_activity")
    private Activity offerActivity;
}
