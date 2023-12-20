package com.example.gymcenterapp.entities;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @ElementCollection
    @ManyToMany(mappedBy = "subscriptionMembers")
    @JoinTable(
        name = "members_subscriptions",
        joinColumns = @JoinColumn(name = "memberId",referencedColumnName = "memberId"),
        inverseJoinColumns = @JoinColumn(name = "subscriptionId",referencedColumnName = "subId")
    )
    private List<Subscription> memberSubscriptions;

    @ElementCollection
    @ManyToMany(mappedBy = "sessionMembers")
    @JoinTable(
        name = "members_sessions",
        joinColumns = @JoinColumn(name = "memberId",referencedColumnName = "memberId"),
        inverseJoinColumns = @JoinColumn(name = "sessionId",referencedColumnName = "sessionId")
    )
    private List<Session> memberSessions;
}
