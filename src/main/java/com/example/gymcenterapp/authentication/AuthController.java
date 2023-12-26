package com.example.gymcenterapp.authentication;

import com.example.gymcenterapp.services.UserDetailsPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController
{
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest)
    {

        try
        {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            System.out.println("authRequest: " + authRequest.getEmail());

            UserDetailsPrincipal user = (UserDetailsPrincipal) authentication.getPrincipal();

            System.out.println("user: " + user.getUserEmail());

            String token = jwtTokenUtils.generateAccessToken(user);
            AuthResponse authResponse = new AuthResponse(user.getUserEmail(), token);

            return ResponseEntity.ok(authResponse);
        }
        catch (BadCredentialsException exception)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
