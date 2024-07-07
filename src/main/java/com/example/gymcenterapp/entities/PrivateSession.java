package com.example.gymcenterapp.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Embeddable
public class PrivateSession
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long privateSessionId;

    private String privateSessionTitle;

    private Date privateSessionStartDateTime;

    private Date privateSessionEndDateTime;

    private Boolean privateSessionIsReserved = false;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member privateSessionMember;


    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach privateSessionCoach;
}
