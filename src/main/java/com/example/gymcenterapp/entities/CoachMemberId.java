package com.example.gymcenterapp.entities;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CoachMemberId implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Long memberId;
    private Long coachId;
}
