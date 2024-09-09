package com.example.gymcenterapp.entities;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Embeddable
public class Coach extends User
{
    @ElementCollection
    private List<MyGrantedAuthority> authorities;

    @ManyToMany
    @JoinTable(
            name = "coaches_activities",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "activityCoaches",referencedColumnName = "actId")
    )
    @JsonIgnore
    private Set<Activity> coachSpecialities;


    @OneToMany(mappedBy = "sessionCoach", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Session> coachSessions;

    @ManyToMany
    @JoinTable(
            name = "private_coaches_members",
            joinColumns = @JoinColumn(name = "privateCoaches",referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "privateMembers",referencedColumnName = "user_id"))
    @JsonIgnore
    private Set<Member> privateMembers;


    @OneToMany(mappedBy = "coach", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<NotificationMemberCoach> notificationMemberCoaches = new HashSet<>();


    @OneToMany(mappedBy = "privateSessionCoach", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<PrivateSession> coachPrivateSessions;
}
