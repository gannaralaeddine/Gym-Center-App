package com.example.gymcenterapp.email.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.frontend")
public class AppFrontendProperties
{
    private String baseUrl = "http://localhost:4200";
    private String loginUrl = "http://localhost:4200/login";
    private boolean autoRedirectAfterVerification = true;
    private int verificationRedirectDelaySeconds = 5;
}
