package com.example.gymcenterapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Embeddable
public class TrainingHistory implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "checkin_time")
    private Date checkInTime;

    @JoinColumn(name = "checkout_time")
    private Date checkOutTime;

    @JoinColumn(name = "is_checkedin")
    private Boolean isCheckedIn = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
