package com.example.gymcenterapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Activité")
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId",referencedColumnName = "catId")
    private Category category;

    @ElementCollection
    @OneToMany(mappedBy = "activity")
    private  List<Subscription> actSubscriptions;

    @ElementCollection
    @OneToMany(mappedBy = "sessionActivity")
    private  List<Session> actSessions;

    @ElementCollection
    @ManyToMany(mappedBy = "coachSpecialities")
    private List<Coach> actCoaches;
}
