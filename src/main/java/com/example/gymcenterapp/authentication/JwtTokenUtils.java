package com.example.gymcenterapp.authentication;

import com.example.gymcenterapp.services.UserDetailsPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenUtils
{
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; //24 Hours

    @Value("${app.jwt.secret}")
    private String secretKey;

    public String generateAccessToken(UserDetailsPrincipal user)
    {
        return Jwts.builder()
                .setSubject(user.getUserId() + "," + user.getUserEmail() + "," + user.getAuthorities())
                .setIssuer(new Date().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
