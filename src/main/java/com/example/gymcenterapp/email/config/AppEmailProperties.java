package com.example.gymcenterapp.email.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.email")
public class AppEmailProperties
{
    private String from = "noreply@gymcenter.com";
    private String support = "support@gymcenter.com";
    private String brandName = "Gym Center";
    private int passwordResetCodeExpiryMinutes = 15;
    private ThemeColors theme = new ThemeColors();

    @Getter
    @Setter
    public static class ThemeColors
    {
        private String primaryColor = "#1e40af";
        private String backgroundColor = "#f1f5f9";
        private String textColor = "#1e293b";
        private String buttonColor = "#2563eb";
        private String buttonTextColor = "#ffffff";
        private String mutedTextColor = "#64748b";
        private String cardBackgroundColor = "#ffffff";
    }
}
