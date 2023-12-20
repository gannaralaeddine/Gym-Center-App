package com.example.gymcenterapp.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "Classe")
public class Session 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId; 

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activityId",referencedColumnName = "actId")
    private Activity sessionActivity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coachId",referencedColumnName = "coachId")
    private Coach sessionCoach;

    @ElementCollection
    @ManyToMany(mappedBy = "memberSessions")
    private List<Member> sessionMembers; 
}
