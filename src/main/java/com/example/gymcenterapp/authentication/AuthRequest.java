package com.example.gymcenterapp.authentication;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AuthRequest
{

    @Length(min = 6, max = 30)
    private String email;

    @Length(min = 6, max = 20)
    private String password;
}
