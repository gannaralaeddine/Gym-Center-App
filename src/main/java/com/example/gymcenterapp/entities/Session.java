package com.example.gymcenterapp.entities;

import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
public class Session 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @Column(name = "session_name")
    private String sessionName;

    @Column(name = "session_description")
    private String sessionDescription;

    @Column(name = "session_start_date")
    private Date sessionStartDate;

    @Column(name = "session_total_places")
    private Integer sessionTotalPlaces;

    @Column(name = "session_reserved_places")
    private Integer sessionReservedPlaces;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "activityId")
    private Activity sessionActivity;
    
    @JoinColumn(name = "session_image")
    private String sessionImage;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "user_id")
    private Coach sessionCoach;

    @JsonIgnore
    @ManyToMany(mappedBy = "memberSessions")
    private Set<Member> sessionMembers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "session_images",
        joinColumns = { @JoinColumn (name = "session_id") },
        inverseJoinColumns = { @JoinColumn(name = "image_id") }
    )
    private Set<ImageModel> sessionImages;

}
