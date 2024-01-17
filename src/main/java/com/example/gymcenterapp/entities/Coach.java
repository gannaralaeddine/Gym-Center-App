package com.example.gymcenterapp.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "coaches_activities",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "activityCoaches",referencedColumnName = "actId")
    )
    private List<Activity> coachSpecialities;


    @OneToMany(mappedBy = "sessionCoach")
//    @JoinColumn(name = "sessionCoach")
    @JsonIgnore
    private List<Session> coachSessions;

}
