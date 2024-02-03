package com.example.gymcenterapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Embeddable
public class Subscription implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "subscription_id")
    private Long subscriptionId;

    @JoinColumn(name = "subscription_price")
    private float subscriptionPrice;

    @JoinColumn(name = "subscription_start_date")
    private Date subscriptionStartDate;

    @JoinColumn(name = "subscription_end_date")
    private Date subscriptionEndDate;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "activity_id",referencedColumnName = "actId")
    private Activity subscriptionActivity;

    @ManyToMany(mappedBy = "memberSubscriptions")
    private Set<Member> subscriptionMembers;
}
