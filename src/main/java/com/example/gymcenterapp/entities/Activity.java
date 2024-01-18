package com.example.gymcenterapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
    private  Set<Subscription> actSubscriptions;


    @OneToMany(mappedBy = "sessionActivity")
//    @JoinColumn(name = "sessionId")
    @JsonIgnore
    private  Set<Session> actSessions;


    @ManyToMany(mappedBy = "coachSpecialities")
    private Set<Coach> actCoaches;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "activity_images",
            joinColumns = { @JoinColumn (name = "activity_id") },
            inverseJoinColumns = { @JoinColumn(name = "image_id") }
    )
    private Set<ImageModel> activityImages;
}
