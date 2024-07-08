package com.example.gymcenterapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Utilisateur")
public class User implements Serializable, UserDetails
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    @Email
    private String userEmail;

    private String userFirstName;

    private String userLastName;

    @Column(length = 510)
    private String userDescription;

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

    private Boolean userIsSubscribed = false;


    @Column(nullable = true)
    private boolean userIsEnabled;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = {@JoinColumn (name = "fk_user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = { @JoinColumn(name = "fk_role_id", referencedColumnName = "role_id") }
    )
    private Set<Role> roles = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinTable(name = "user_images",
            joinColumns = { @JoinColumn (name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "image_id") }
    )
    private Set<ImageModel> userImages;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role: roles)
        {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }


    public void addRole(Role role)
    {
        this.roles.add(role);
    }


}
