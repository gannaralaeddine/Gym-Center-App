package com.example.gymcenterapp.authentication;

import com.example.gymcenterapp.services.UserDetailsPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenUtils
{

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; //24 Hours

    @Value("${app.jwt.secret}")
    private String secretKey;

    public String generateAccessToken(UserDetailsPrincipal user)
    {
        return Jwts.builder()
                .setSubject(user.getUserId() + "," + user.getUserEmail())
                .claim("roles", user.getAuthorities().toString())
                .setIssuer(new Date().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateAccessToken(String token)
    {
        try
        {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }
        catch (ExpiredJwtException ex)
        {
         logger.error("JWT expired: ", ex);
        }
        catch (IllegalArgumentException ex)
        {
            logger.error("Token is null, empty or has only whitespace: ", ex);
        }
        catch (MalformedJwtException ex)
        {
            logger.error("Jwt is invalid: ", ex);
        }
        catch (UnsupportedJwtException ex)
        {
            logger.error("JWT is not supported: ", ex);
        }
        catch (SignatureException ex)
        {
            logger.error("Signature validation failed: ", ex);
        }
        return false;
    }

    public String getSubject(String token)
    {
        return parseClaims(token).getSubject();
    }

    public Claims parseClaims(String token)
    {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody() ;
    }
}
