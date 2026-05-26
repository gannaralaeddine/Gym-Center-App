package com.example.gymcenterapp.email.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableConfigurationProperties({
        AppBackendProperties.class,
        AppFrontendProperties.class,
        AppEmailProperties.class
})
public class EmailConfiguration
{
}
