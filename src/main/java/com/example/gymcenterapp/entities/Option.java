package com.example.gymcenterapp.entities;

import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "offer_option",
        joinColumns = { @JoinColumn (name = "option_id") },
        inverseJoinColumns = { @JoinColumn(name = "offer_id") }
    )
    @JsonIgnore
    private List<Offer> optionOffer;
}
