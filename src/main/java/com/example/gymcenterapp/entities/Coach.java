package com.example.gymcenterapp.entities;

import javax.persistence.*;

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
@Table(name = "Coach")
@Embeddable
public class Coach extends User
{

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "coaches_activities",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "activityCoaches",referencedColumnName = "actId")
    )
    private List<Activity> coachSpecialities;


    @OneToMany(mappedBy = "sessionCoach")
    private List<Session> coachSessions;

}
