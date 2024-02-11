package com.example.gymcenterapp.entities;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

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
public class Option 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "option_id")
    private Long optionId;

    @Size(max = 50)
    @JoinColumn(name = "option_name")
    private String optionName;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "option_offer")
    private Offer optionOffer;
}
