package com.example.gymcenterapp.email.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.backend")
public class AppBackendProperties
{
    private String baseUrl = "http://localhost:8089/gym-center";
}
