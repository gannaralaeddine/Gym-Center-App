package com.example.gymcenterapp.entities;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMemberCoach implements Serializable
{
    @EmbeddedId
    private CoachMemberId coachMemberId = new CoachMemberId();

    private String notificationTitle;

    private String notificationContent;

    @ManyToOne
    @MapsId("coachId")
    private Coach coach;

    @ManyToOne
    @MapsId("memberId")
    private Member member;





}

