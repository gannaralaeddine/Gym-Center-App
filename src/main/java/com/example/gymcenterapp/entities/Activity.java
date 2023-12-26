package com.example.gymcenterapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Embeddable
public class Activity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "activityId")
    private Long actId;

    @JoinColumn(name = "activityName")
    private String actName;

    @JoinColumn(name = "activityDescription")
    private String actDescription;

    @JoinColumn(name = "activityImage")
    private String actImage;


    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "categoryId")
    private Category category;


    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    private  List<Subscription> actSubscriptions;


    @OneToMany(mappedBy = "sessionActivity")
    @JsonIgnore
    private  List<Session> actSessions;


    @ManyToMany(mappedBy = "coachSpecialities")
    private List<Coach> actCoaches;
}
