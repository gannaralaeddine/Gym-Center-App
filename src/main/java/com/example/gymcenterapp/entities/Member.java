package com.example.gymcenterapp.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

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
public class Member extends User
{
    @ElementCollection
    private List<MyGrantedAuthority> authorities;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private Set<Subscription> memberSubscriptions;


    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "members_sessions",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sessionId",referencedColumnName = "sessionId")
    )
    private Set<Session> memberSessions;

    @ManyToMany(mappedBy = "privateMembers")
    private Set<Coach> privateCoaches;

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private Set<NotificationMemberCoach> notificationMemberCoaches = new HashSet<>();


    @OneToMany(mappedBy = "privateSessionMember")
    @JsonIgnore
    private Set<PrivateSession> memberPrivateSessions;
}
