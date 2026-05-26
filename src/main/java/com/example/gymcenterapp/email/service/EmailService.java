package com.example.gymcenterapp.email.service;

import com.example.gymcenterapp.entities.ConfirmationToken;
import com.example.gymcenterapp.entities.PrivateSession;
import com.example.gymcenterapp.entities.Session;
import com.example.gymcenterapp.entities.Subscription;
import com.example.gymcenterapp.entities.User;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService
{
    ConfirmationToken sendAccountVerificationEmail(User user);

    void sendPasswordResetEmail(String recipientEmail, String recipientName, int verificationCode);

    void sendEmail(SimpleMailMessage email);

    void sendCoachBookingNotificationEmail(PrivateSession privateSession);

    void sendCancelPrivateSessionEmail(PrivateSession privateSession);

    void sendCancelSubscriptionEmail(Subscription subscription);

    void sendConfirmationSubscriptionEmail(Subscription subscription);

    void sendCancelSessionEmail(Session session);
}
