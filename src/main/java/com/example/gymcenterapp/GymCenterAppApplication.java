package com.example.gymcenterapp;

import com.example.gymcenterapp.authentication.JwtTokenFilter;
import com.example.gymcenterapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = false,
        jsr250Enabled = true
)
@SpringBootApplication//(exclude = { SecurityAutoConfiguration.class })
public class GymCenterAppApplication extends WebSecurityConfigurerAdapter
{

    UserService userService;

    JwtTokenFilter jwtTokenFilter;

    public static void main(String[] args) { SpringApplication.run(GymCenterAppApplication.class, args); }

    @Bean
    PasswordEncoder bcryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
    {
        auth.authenticationProvider(daoAuthenticationProvider());

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider ()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(bcryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userService);
        return daoAuthenticationProvider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.exceptionHandling().authenticationEntryPoint( (request, response, exception) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
        } );

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .csrf().disable()
                .authorizeRequests()

                .antMatchers("/auth/login").permitAll()

                .antMatchers("/public/**").permitAll()
                .antMatchers("static/**").permitAll()

//                .anyRequest().authenticated();
                .antMatchers("/hhh").permitAll()
                .antMatchers("/notify").permitAll()
                .antMatchers("/socket").permitAll()

                .antMatchers("/users/*").permitAll()
                .antMatchers("/categories/*").permitAll()

                .antMatchers("/user/retrieve-all-users").permitAll()//.hasAnyRole("ADMIN")
                .antMatchers("/user/register-user").permitAll()
                .antMatchers("/user/add-role").permitAll()
                .antMatchers("/user/number-of-users").permitAll()
                .antMatchers("/user/retrieve-all-roles").permitAll()
                .antMatchers("/user/retrieve-user-by-email/{email}").permitAll()
                .antMatchers("/user/retrieve-user/{user-id}").permitAll()
                .antMatchers("/user/update-profile-picture").permitAll()
                .antMatchers("/user/update-user").permitAll()
                .antMatchers("/user/add-images-to-user").permitAll()
                .antMatchers("/user/delete-user-image/{userId}/{imageName}").permitAll()
                .antMatchers("/user/confirm-account").permitAll()
                .antMatchers("/user/send-contact-us-email").permitAll()
                .antMatchers("/user/send-verification-code/{email}").permitAll()
                .antMatchers("/user/check-verification-code/{code}").permitAll()
                .antMatchers("/user/change-password/{email}/{password}").permitAll()

                .antMatchers("/category/add-category").permitAll()
                .antMatchers("/category/update-category").permitAll()
                .antMatchers("/category/update-category/{id}").permitAll()
                .antMatchers("/category/delete-category/{id}").permitAll()
                .antMatchers("/category/retrieve-category/{id}").permitAll()
                .antMatchers("/category/retrieve-all-categories").permitAll()
                .antMatchers("/category/create-category").permitAll()
                .antMatchers("/category/add-images-to-category").permitAll()

                .antMatchers("/coach/register-coach").permitAll()
                .antMatchers("/coach/update-coach/{coach-id}").permitAll()
                .antMatchers("/coach/delete-coach/{coach-id}").permitAll()
                .antMatchers("/coach/retrieve-coach/{coach-id}").permitAll()
                .antMatchers("/coach/retrieve-coach-by-email/{email}").permitAll()
                .antMatchers("/coach/retrieve-all-coaches").permitAll()
                .antMatchers("/coach/add-coach-to-activity/{coach-id}").permitAll()
                .antMatchers("/coach/retrieve-coach-specialities/{coach-id}").permitAll()
                .antMatchers("/coach/delete-coach-activities/{coach-id}/{activity-id}").permitAll()
                .antMatchers("/coach/retrieve-coach-sessions/{email}").permitAll()
                .antMatchers("/coach/retrieve-private-members/{coachEmail}").permitAll()
                .antMatchers("/coach/terminate-coach-member-relation/{memberEmail}/{coachEmail}").permitAll()
                .antMatchers("/coach/getCoachNotifications/{coachEmail}").permitAll()
                .antMatchers("/coach/get-coach-private-sessions/{coachEmail}").permitAll()

                .antMatchers("/member/register-member").permitAll()
                .antMatchers("/member/update-member/{member-id}").permitAll()
                .antMatchers("/member/delete-member/{member-id}").permitAll()
                .antMatchers("/member/retrieve-member/{email}").permitAll()
                .antMatchers("/member/retrieve-all-members").permitAll()
                .antMatchers("/member/retrieve-member-sessions/{email}").permitAll()
                .antMatchers("/member/private-coach-booking/{memberEmail}/{coachEmail}").permitAll()
                .antMatchers("/member/retrieve-private-coaches/{memberEmail}").permitAll()
                .antMatchers("/member/is-my-private-coach/{memberEmail}/{coachEmail}").permitAll()
                .antMatchers("/member/send-invitation-to-coach/{memberEmail}/{coachEmail}").permitAll()
                .antMatchers("/member/getMemberNotifications/{memberEmail}").permitAll()
                .antMatchers("/member/coach-booking/{memberEmail}/{coachEmail}").permitAll()
                .antMatchers("/member/get-member-private-sessions/{memberEmail}").permitAll()
                .antMatchers("/member/get-member-subscriptions/{memberEmail}").permitAll()
                .antMatchers("/member/retrieve-member-by-id/{id}").permitAll()
                .antMatchers("/member/update-member-private-sessions-number/{memberEmail}/{newPrivateSessionsNumber}").permitAll()
                .antMatchers("/member/replace-old-member-private-sessions-number/{member-id}/{newPrivateSessionsNumber}").permitAll()

                .antMatchers("/session/create-session").permitAll()
                .antMatchers("/session/update-session/{session-id}").permitAll()
                .antMatchers("/session/delete-session/{session-id}").permitAll()
                .antMatchers("/session/retrieve-session/{session-id}").permitAll()
                .antMatchers("/session/retrieve-all-sessions").permitAll()
                .antMatchers("/session/update-session-with-image").permitAll()
                .antMatchers("/session/add-images-to-session").permitAll()
                .antMatchers("/session/delete-session-image/{sessionId}/{imageName}").permitAll()
                .antMatchers("/session/assign-member-to-session/{email}/{sessionId}").permitAll()
                .antMatchers("/session/remove-member-from-session/{email}/{sessionId}").permitAll()
                .antMatchers("/session/is-member-participated-to-session/{email}/{sessionId}").permitAll()

                .antMatchers("/subscription/create-subscription/{member-id}").permitAll()
                .antMatchers("/subscription/update-subscription/{subscription-id}/{member-id}").permitAll()
                .antMatchers("/subscription/delete-subscription/{subscription-id}").permitAll()
                .antMatchers("/subscription/retrieve-subscription/{subscription-id}").permitAll()
                .antMatchers("/subscription/retrieve-all-subscriptions").permitAll()
                .antMatchers("/subscription/retrieve-activity-subscriptions/{activity-id}").permitAll()
                .antMatchers("/subscription/assign-member-to-subscription/{subscriptionId}/{memberId}").permitAll()
                

                .antMatchers("/activity/retrieve-all-activities").permitAll()
                .antMatchers("/activity/add-activity").permitAll()
                .antMatchers("/activity/update-activity/{activity-id}").permitAll()
                .antMatchers("/activity/update-activity").permitAll()
                .antMatchers("/activity/delete-activity/{activity-id}").permitAll()
                .antMatchers("/activity/retrieve-activity/{activity-id}").permitAll()
                .antMatchers("/activity/get-category-activities/{id}").permitAll()
                .antMatchers("/activity/create-activity").permitAll()
                .antMatchers("/activity/add-images-to-activity").permitAll()
                .antMatchers("/activity/delete-activity-image/{actId}/{imageName}").permitAll()
                .antMatchers("/activity/add-coach-to-activity/{coachId}/{activityId}").permitAll()
                .antMatchers("/image/get-image/{image-name}").permitAll()

                .antMatchers("/offer/add-offer").permitAll()
                .antMatchers("/offer/retrieve-offer/{offer-id}").permitAll()
                .antMatchers("/offer/retrieve-all-offers").permitAll()
                .antMatchers("/offer/update-offer/{offer-id}").permitAll()
                .antMatchers("/offer/delete-offer/{offer-id}").permitAll()

                .antMatchers("/option/retrieve-all-options").permitAll()
                .antMatchers("/option/retrieve-option/{option-id}").permitAll()
                .antMatchers("/option/create-option").permitAll()
                .antMatchers("/option/update-option/{option-id}").permitAll()
                .antMatchers("/option/delete-option/{option-id}").permitAll()

                .antMatchers("/private-session/add-private-session").permitAll()
                .antMatchers("/private-session/retrieve-all-private-sessions").permitAll()
                .antMatchers("/private-session/retrieve-private-session/{id}").permitAll()
                .antMatchers("/private-session/cancel-private-session/{memberEmail}/{privateSessionId}").permitAll()

                .anyRequest().authenticated().and().httpBasic();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:4500"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
