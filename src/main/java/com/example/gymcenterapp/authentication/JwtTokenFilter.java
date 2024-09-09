package com.example.gymcenterapp.authentication;

import com.example.gymcenterapp.entities.Role;
import com.example.gymcenterapp.entities.User;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter
{

    JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {


        if (!hasAuthorizationHeader(request))
        {
            filterChain.doFilter(request, response);
            return;
        }


        String accessToken = getAccessToken(request);

        if (!jwtTokenUtils.validateAccessToken(accessToken))
        {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContext(accessToken, request);
        filterChain.doFilter(request, response);

        System.out.println("Access Token: " + accessToken);
    }

    private boolean hasAuthorizationHeader(HttpServletRequest request)
    {
        String header = request.getHeader("Authorization");
        System.out.println("Authorization header: " + header);

        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer"))
        {
            return false;
        }
        return true;
    }

    private String getAccessToken(HttpServletRequest request)
    {
        String header = request.getHeader("Authorization");

        String token = header.split(" ")[1].trim();
        return token;
    }

    private void setAuthenticationContext(String accessToken, HttpServletRequest request)
    {
        UserDetails userDetails = getUserDetails(accessToken);

        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String accessToken)
    {
        User userDetails = new User();

        Claims claims = jwtTokenUtils.parseClaims(accessToken);

        String claimRoles = (String) claims.get("roles");

        System.out.println("claimRoles: " + claimRoles);

        claimRoles = claimRoles.replace("[", "").replace("]", "");

        String[] rolesNames = claimRoles.split(",");

        for (String roleName: rolesNames)
        {
            userDetails.addRole(new Role(roleName));
        }

        String subject = (String) claims.get(Claims.SUBJECT);

        String[] subjectArray = subject.split(",");

        userDetails.setUserId(Long.parseLong(subjectArray[0]));
        userDetails.setUserEmail(subjectArray[1]);

//        System.out.println("subjectArray roles: " + subjectArray[2]);

        return userDetails;
    }
}
