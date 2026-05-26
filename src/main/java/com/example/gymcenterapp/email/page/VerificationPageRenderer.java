package com.example.gymcenterapp.email.page;

import com.example.gymcenterapp.email.config.AppFrontendProperties;
import com.example.gymcenterapp.email.template.EmailTemplateBuilder;
import com.example.gymcenterapp.email.theme.EmailTheme;
import com.example.gymcenterapp.email.theme.EmailThemeManager;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class VerificationPageRenderer
{
    private static final String SUCCESS_PAGE = "templates/pages/verification-success.html";
    private static final String ERROR_PAGE = "templates/pages/verification-error.html";

    private final EmailTemplateBuilder templateBuilder;
    private final EmailThemeManager themeManager;
    private final AppFrontendProperties frontendProperties;

    public VerificationPageRenderer(
            EmailTemplateBuilder templateBuilder,
            EmailThemeManager themeManager,
            AppFrontendProperties frontendProperties)
    {
        this.templateBuilder = templateBuilder;
        this.themeManager = themeManager;
        this.frontendProperties = frontendProperties;
    }

    public String renderSuccess(String userName)
    {
        Map<String, String> placeholders = basePagePlaceholders(themeManager.getBrandTheme());
        placeholders.put("userName", userName == null || userName.trim().isEmpty() ? "" : " " + userName.trim());
        placeholders.put("loginUrl", frontendProperties.getLoginUrl());
        placeholders.put("autoRedirect", String.valueOf(frontendProperties.isAutoRedirectAfterVerification()));
        placeholders.put("redirectDelaySeconds", String.valueOf(frontendProperties.getVerificationRedirectDelaySeconds()));
        placeholders.put("redirectUrl", frontendProperties.getLoginUrl());
        return templateBuilder.buildPage(SUCCESS_PAGE, placeholders);
    }

    public String renderError()
    {
        Map<String, String> placeholders = basePagePlaceholders(themeManager.getBrandTheme());
        placeholders.put("loginUrl", frontendProperties.getLoginUrl());
        return templateBuilder.buildPage(ERROR_PAGE, placeholders);
    }

    private Map<String, String> basePagePlaceholders(EmailTheme theme)
    {
        Map<String, String> placeholders = new LinkedHashMap<>();
        placeholders.put("primaryColor", theme.getPrimaryColor());
        placeholders.put("backgroundColor", theme.getBackgroundColor());
        placeholders.put("textColor", theme.getTextColor());
        placeholders.put("buttonColor", theme.getButtonColor());
        placeholders.put("buttonTextColor", theme.getButtonTextColor());
        placeholders.put("mutedTextColor", theme.getMutedTextColor());
        placeholders.put("cardBackgroundColor", theme.getCardBackgroundColor());
        placeholders.put("year", String.valueOf(Year.now().getValue()));
        return placeholders;
    }
}
