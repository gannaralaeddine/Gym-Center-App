package com.example.gymcenterapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @ManyToOne
    @JoinColumn(name = "activity_id",referencedColumnName = "actId")
    private Activity subscriptionActivity;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
