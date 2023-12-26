package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsPrincipal implements UserDetails
{
    private final User user;

    public UserDetailsPrincipal(User user)
    {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();

        user.getRoles().forEach(role -> roles.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));


        return roles;
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public String getUserEmail() {
        return user.getUserEmail();
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserEmail();
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
