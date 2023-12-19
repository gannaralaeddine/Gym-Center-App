package com.example.gymcenterapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Utilisateur")
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userEmail;

    private String username;

    private Date userBirthDate;

    private String userPhoneNumber;

    private String userCity;

    private String userState;

    private String userCountry;

    private String userGender;

    private String userHeight;

    private String userWeight;

    private String userPicture;

    private String userZipCode;

    private String userPassword;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "user_roles",
        joinColumns = {@JoinColumn (name = "fk_user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = { @JoinColumn(name = "fk_role_id", referencedColumnName = "role_id") }
    )
    private Set<Role> roles = new HashSet<>();

}
