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

//                .anyRequest().authenticated();

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
                .antMatchers("/coach/retrieve-all-coaches").permitAll()

                .antMatchers("/member/register-member").permitAll()
                .antMatchers("/member/update-member/{member-id}").permitAll()
                .antMatchers("/member/delete-member/{member-id}").permitAll()
                .antMatchers("/member/retrieve-member/{member-id}").permitAll()
                .antMatchers("/member/retrieve-all-members").permitAll()

                .antMatchers("/session/create-session").permitAll()
                .antMatchers("/session/update-session/{session-id}").permitAll()
                .antMatchers("/session/delete-session/{session-id}").permitAll()
                .antMatchers("/session/retrieve-session/{session-id}").permitAll()
                .antMatchers("/session/retrieve-all-sessions").permitAll()
                .antMatchers("/session/update-session-with-image").permitAll()
                .antMatchers("/session/add-images-to-session").permitAll()
                .antMatchers("/session/delete-session-image/{sessionId}/{imageName}").permitAll()

                .antMatchers("/subscription/create-subscription").permitAll()
                .antMatchers("/subscription/update-subscription/{subscription-id}").permitAll()
                .antMatchers("/subscription/delete-subscription/{subscription-id}").permitAll()
                .antMatchers("/subscription/retrieve-subscription/{subscription-id}").permitAll()
                .antMatchers("/subscription/retrieve-all-subscriptions").permitAll()
                .antMatchers("/subscription/add-member-to-subscription/{subscriptionId}/{memberId}").permitAll()
                

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

                .anyRequest().authenticated().and().httpBasic();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
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
