package com.example.gymcenterapp;

import com.example.gymcenterapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@EnableWebSecurity
@AllArgsConstructor
@SpringBootApplication//(exclude = { SecurityAutoConfiguration.class })
public class GymCenterAppApplication extends WebSecurityConfigurerAdapter
{

    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(GymCenterAppApplication.class, args);
    }

    @Bean
    PasswordEncoder bcryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
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
        http
                .csrf().disable()
                .authorizeRequests()

                .antMatchers("/auth/login").permitAll()

                .antMatchers("/user/retrieve-all-users").permitAll()//.hasAnyRole("ADMIN")
                .antMatchers("/user/register-user").permitAll()
                .antMatchers("/user/add-role").permitAll()
                .antMatchers("/user/number-of-users").permitAll()

                .antMatchers("/category/add-category").permitAll()
                .antMatchers("/category/update-category/{id}").permitAll()
                .antMatchers("/category/delete-category/{id}").permitAll()
                .antMatchers("/category/retrieve-category/{id}").permitAll()    
                .antMatchers("/category/retrieve-all-categories").permitAll()
                .antMatchers("/category/upload-image").permitAll()
                .antMatchers("/category/get-image/{image-name}").permitAll()

                .antMatchers("/coach/add-coach").permitAll()
                .antMatchers("/coach/update-coach/{coach-id}").permitAll()
                .antMatchers("/coach/delete-coach/{coach-id}").permitAll()
                .antMatchers("/coach/retrieve-coach/{coach-id}").permitAll()    
                .antMatchers("/coach/retrieve-all-coaches").permitAll()

                .antMatchers("/member/add-member").permitAll()
                .antMatchers("/member/update-member/{member-id}").permitAll()
                .antMatchers("/member/delete-member/{member-id}").permitAll()
                .antMatchers("/member/retrieve-member/{member-id}").permitAll()    
                .antMatchers("/member/retrieve-all-members").permitAll()

                .antMatchers("/session/add-session").permitAll()
                .antMatchers("/session/update-session/{session-id}").permitAll()
                .antMatchers("/session/delete-session/{session-id}").permitAll()
                .antMatchers("/session/retrieve-session/{session-id}").permitAll()    
                .antMatchers("/session/retrieve-all-sessions").permitAll()

                .antMatchers("/subscription/add-subscription").permitAll()
                .antMatchers("/subscription/update-subscription/{subscription-id}").permitAll()
                .antMatchers("/subscription/delete-subscription/{subscription-id}").permitAll()
                .antMatchers("/subscription/retrieve-subscription/{subscription-id}").permitAll()    
                .antMatchers("/subscription/retrieve-all-subscriptions").permitAll()

                .antMatchers("/activity/retrieve-all-activities").permitAll()
                .antMatchers("/activity/add-activity").permitAll()
                .antMatchers("/activity/update-activity/{activity-id}").permitAll()
                .antMatchers("/activity/delete-activity/{activity-id}").permitAll()
                .antMatchers("/activity/retrieve-activity/{activity-id}").permitAll()
                .antMatchers("/activity/get-category-activities/{id}").permitAll()

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
