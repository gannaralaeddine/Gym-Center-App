package com.example.gymcenterapp.email.theme;

import com.example.gymcenterapp.email.config.AppEmailProperties;
import org.springframework.stereotype.Component;

@Component
public class EmailThemeManager
{
    private final AppEmailProperties emailProperties;

    public EmailThemeManager(AppEmailProperties emailProperties)
    {
        this.emailProperties = emailProperties;
    }

    /**
     * Returns the single brand theme used by every email and verification page.
     */
    public EmailTheme getBrandTheme()
    {
        AppEmailProperties.ThemeColors colors = emailProperties.getTheme();
        return new EmailTheme(
                colors.getPrimaryColor(),
                colors.getBackgroundColor(),
                colors.getTextColor(),
                colors.getButtonColor(),
                colors.getButtonTextColor(),
                colors.getMutedTextColor(),
                colors.getCardBackgroundColor()
        );
    }
}
