package com.example.gymcenterapp.email.template;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailTemplateVariables
{
    private final String userName;
    private final String verificationCode;
    private final String actionUrl;
    private final String appName;
    private final String supportEmail;
    private final String expiryMinutes;
    private final String year;
    private final String headline;
    private final String introText;
    private final String footerText;
    private final String ctaLabel;
    private final String activityName;
    private final String startDate;
    private final String endDate;
    private final String subscriptionPrice;
}
