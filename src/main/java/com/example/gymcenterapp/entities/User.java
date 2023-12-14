package com.example.gymcenterapp.entities;

import com.example.gymcenterapp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userEmail;

    private String userName;

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

    @ElementCollection
    private List<Role> userRoles;
}
