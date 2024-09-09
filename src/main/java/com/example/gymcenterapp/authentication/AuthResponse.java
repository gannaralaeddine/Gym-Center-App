package com.example.gymcenterapp.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse
{
    private String email;
    private String token;
    private Collection<? extends GrantedAuthority> authorities;
}
