package com.example.gymcenterapp.entities;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "coachId")
    private Long coachId;

    @ElementCollection
    @ManyToMany(mappedBy = "actCoaches")
    @JoinTable(
        name = "coaches_activities",
        joinColumns = @JoinColumn(name = "coachId",referencedColumnName = "coachId"),
        inverseJoinColumns = @JoinColumn(name = "activityCoaches",referencedColumnName = "actCoaches")
    )
    private List<Activity> coachSpecialities;

    @ElementCollection
    @OneToMany(mappedBy = "sessionCoach")
    private List<Session> coachSessions;
}
