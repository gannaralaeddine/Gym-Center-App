package com.example.gymcenterapp.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "confirmationTokens")
public class ConfirmationToken
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;


    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;


    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    public ConfirmationToken() {}

    public ConfirmationToken(User user) {
        this.user = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}
