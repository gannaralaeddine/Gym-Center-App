package com.example.gymcenterapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    @JoinColumn(name = "subscriptionId")
    private Long subId;

    @JoinColumn(name = "subscription_price")
    private float subPrice;

    @JoinColumn(name = "subscription_start")
    private Date subscriptionStart;

    @JoinColumn(name = "subscription_end")
    private Date subscriptionEnd;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activityId",referencedColumnName = "actId")
    private Activity activity;


    @ManyToMany(mappedBy = "memberSubscriptions")
    private List<Member> subscriptionMembers;
}
