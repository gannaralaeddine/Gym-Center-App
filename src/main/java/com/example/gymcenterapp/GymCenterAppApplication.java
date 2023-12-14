package com.example.gymcenterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GymCenterAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymCenterAppApplication.class, args);
    }

}
