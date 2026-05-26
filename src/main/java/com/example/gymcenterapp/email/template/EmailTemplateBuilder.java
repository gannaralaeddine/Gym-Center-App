package com.example.gymcenterapp.email.template;

import com.example.gymcenterapp.email.theme.EmailTheme;
import com.example.gymcenterapp.email.theme.EmailThemeManager;
import com.example.gymcenterapp.email.theme.EmailType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class EmailTemplateBuilder
{
    private static final String LAYOUT_PATH = "templates/email/layout.html";
    private static final String ACCOUNT_VERIFICATION_BODY = "templates/email/account-verification-body.html";
    private static final String PASSWORD_RESET_BODY = "templates/email/password-reset-body.html";
    private static final String SUBSCRIPTION_CONFIRMATION_BODY = "templates/email/subscription-confirmation-body.html";

    private final EmailThemeManager themeManager;

    public EmailTemplateBuilder(EmailThemeManager themeManager)
    {
        this.themeManager = themeManager;
    }

    public String buildAccountVerificationEmail(EmailTemplateVariables variables)
    {
        return build(EmailType.ACCOUNT_VERIFICATION, ACCOUNT_VERIFICATION_BODY, variables);
    }

    public String buildPasswordResetEmail(EmailTemplateVariables variables)
    {
        return build(EmailType.PASSWORD_RESET, PASSWORD_RESET_BODY, variables);
    }

    public String buildSubscriptionConfirmationEmail(EmailTemplateVariables variables)
    {
        return build(EmailType.SUBSCRIPTION_CONFIRMATION, SUBSCRIPTION_CONFIRMATION_BODY, variables);
    }

    public String buildPage(String classpathTemplate, Map<String, String> placeholders)
    {
        return applyPlaceholders(loadTemplate(classpathTemplate), placeholders);
    }

    private String build(EmailType emailType, String bodyTemplatePath, EmailTemplateVariables variables)
    {
        EmailTheme theme = themeManager.getBrandTheme();
        Map<String, String> placeholders = new LinkedHashMap<>();
        placeholders.putAll(toThemeMap(theme));
        placeholders.putAll(toVariableMap(variables));

        String body = applyPlaceholders(loadTemplate(bodyTemplatePath), placeholders);
        placeholders.put("emailBody", body);

        return applyPlaceholders(loadTemplate(LAYOUT_PATH), placeholders);
    }

    private Map<String, String> toThemeMap(EmailTheme theme)
    {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("primaryColor", theme.getPrimaryColor());
        map.put("backgroundColor", theme.getBackgroundColor());
        map.put("textColor", theme.getTextColor());
        map.put("buttonColor", theme.getButtonColor());
        map.put("buttonTextColor", theme.getButtonTextColor());
        map.put("mutedTextColor", theme.getMutedTextColor());
        map.put("cardBackgroundColor", theme.getCardBackgroundColor());
        return map;
    }

    private Map<String, String> toVariableMap(EmailTemplateVariables variables)
    {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("userName", safe(variables.getUserName()));
        map.put("verificationCode", safe(variables.getVerificationCode()));
        map.put("actionUrl", safe(variables.getActionUrl()));
        map.put("appName", safe(variables.getAppName()));
        map.put("supportEmail", safe(variables.getSupportEmail()));
        map.put("expiryMinutes", safe(variables.getExpiryMinutes()));
        map.put("year", safe(variables.getYear()));
        map.put("headline", safe(variables.getHeadline()));
        map.put("introText", safe(variables.getIntroText()));
        map.put("footerText", safe(variables.getFooterText()));
        map.put("ctaLabel", safe(variables.getCtaLabel()));
        map.put("activityName", safe(variables.getActivityName()));
        map.put("startDate", safe(variables.getStartDate()));
        map.put("endDate", safe(variables.getEndDate()));
        map.put("subscriptionPrice", safe(variables.getSubscriptionPrice()));
        return map;
    }

    private String applyPlaceholders(String template, Map<String, String> placeholders)
    {
        String result = template;
        for (Map.Entry<String, String> entry : placeholders.entrySet())
        {
            result = result.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return result;
    }

    private String loadTemplate(String classpathLocation)
    {
        try
        {
            ClassPathResource resource = new ClassPathResource(classpathLocation);
            try (InputStream inputStream = resource.getInputStream())
            {
                return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            }
        }
        catch (IOException exception)
        {
            throw new IllegalStateException("Unable to load email template: " + classpathLocation, exception);
        }
    }

    private String safe(String value)
    {
        return value == null ? "" : value;
    }
}
