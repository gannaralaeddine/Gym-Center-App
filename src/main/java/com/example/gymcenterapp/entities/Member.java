package com.example.gymcenterapp.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
public class Member extends User
{
    @JsonIgnore  
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "members_subscriptions",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriptionId",referencedColumnName = "subscriptionId")
    )
    private Set<Subscription> memberSubscriptions;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "members_sessions",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sessionId",referencedColumnName = "sessionId")
    )
    private List<Session> memberSessions;
}
