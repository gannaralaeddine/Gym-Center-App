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
@Table(name = "Abonnement")

public class Subscription implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activityId",referencedColumnName = "actId")
    private Activity activity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId",referencedColumnName = "catId")
    private Category category;
}
