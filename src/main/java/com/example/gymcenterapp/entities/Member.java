package com.example.gymcenterapp.entities;

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
@Table(name = "Membre")
@Embeddable
public class Member extends User
{

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "members_subscriptions",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriptionId",referencedColumnName = "subId")
    )
    private List<Subscription> memberSubscriptions;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "members_sessions",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sessionId",referencedColumnName = "sessionId")
    )
    private List<Session> memberSessions;
}
