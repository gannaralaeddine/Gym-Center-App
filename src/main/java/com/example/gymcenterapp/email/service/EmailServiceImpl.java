package com.example.gymcenterapp.email.service;

import com.example.gymcenterapp.email.config.AppBackendProperties;
import com.example.gymcenterapp.email.config.AppEmailProperties;
import com.example.gymcenterapp.email.config.AppFrontendProperties;
import com.example.gymcenterapp.email.template.EmailTemplateBuilder;
import com.example.gymcenterapp.email.template.EmailTemplateVariables;
import com.example.gymcenterapp.entities.Coach;
import com.example.gymcenterapp.entities.ConfirmationToken;
import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.entities.PrivateSession;
import com.example.gymcenterapp.entities.Session;
import com.example.gymcenterapp.entities.Subscription;
import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.repositories.ConfirmationTokenRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.format.DateTimeFormatter;

@Service
public class EmailServiceImpl implements EmailService
{
    private final JavaMailSender mailSender;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailTemplateBuilder templateBuilder;
    private final AppEmailProperties emailProperties;
    private final AppBackendProperties backendProperties;
    private final AppFrontendProperties frontendProperties;

    public EmailServiceImpl(
            JavaMailSender mailSender,
            ConfirmationTokenRepository confirmationTokenRepository,
            EmailTemplateBuilder templateBuilder,
            AppEmailProperties emailProperties,
            AppBackendProperties backendProperties,
            AppFrontendProperties frontendProperties)
    {
        this.mailSender = mailSender;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.templateBuilder = templateBuilder;
        this.emailProperties = emailProperties;
        this.backendProperties = backendProperties;
        this.frontendProperties = frontendProperties;
    }

    @Override
    public ConfirmationToken sendAccountVerificationEmail(User user)
    {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        String confirmationLink = backendProperties.getBaseUrl()
                + "/user/confirm-account?token="
                + confirmationToken.getConfirmationToken();

        String displayName = buildDisplayName(user);
        EmailTemplateVariables variables = EmailTemplateVariables.builder()
                .userName(displayName)
                .actionUrl(confirmationLink)
                .appName(emailProperties.getBrandName())
                .supportEmail(emailProperties.getSupport())
                .year(String.valueOf(Year.now().getValue()))
                .headline("Confirmez votre compte")
                .introText("Merci de rejoindre " + emailProperties.getBrandName()
                        + ". Pour activer votre compte et garantir la sécurité de vos données, veuillez confirmer votre adresse e-mail.")
                .footerText("Cordialement, l'équipe " + emailProperties.getBrandName())
                .ctaLabel("VERIFIER MON COMPTE")
                .build();

        sendHtmlEmail(
                user.getUserEmail(),
                "Confirmez votre compte - " + emailProperties.getBrandName(),
                templateBuilder.buildAccountVerificationEmail(variables)
        );

        return confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public void sendPasswordResetEmail(String recipientEmail, String recipientName, int verificationCode)
    {
        String displayName = recipientName == null || recipientName.trim().isEmpty() ? "Utilisateur" : recipientName;

        EmailTemplateVariables variables = EmailTemplateVariables.builder()
                .userName(displayName)
                .verificationCode(String.valueOf(verificationCode))
                .appName(emailProperties.getBrandName())
                .supportEmail(emailProperties.getSupport())
                .expiryMinutes(String.valueOf(emailProperties.getPasswordResetCodeExpiryMinutes()))
                .year(String.valueOf(Year.now().getValue()))
                .headline("Réinitialisation du mot de passe")
                .introText("Vous avez demandé à réinitialiser votre mot de passe. Utilisez le code ci-dessous dans l'application.")
                .footerText("Si vous n'avez pas effectué cette demande, ignorez cet e-mail.")
                .build();

        sendHtmlEmail(
                recipientEmail,
                "Code de récupération - " + emailProperties.getBrandName(),
                templateBuilder.buildPasswordResetEmail(variables)
        );
    }

    @Override
    @Async
    public void sendEmail(SimpleMailMessage email)
    {
        if (email.getFrom() == null || email.getFrom().isEmpty())
        {
            email.setFrom(emailProperties.getFrom());
        }
        mailSender.send(email);
    }

    @Override
    public void sendCoachBookingNotificationEmail(PrivateSession privateSession)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        Coach coach = privateSession.getPrivateSessionCoach();
        Member member = privateSession.getPrivateSessionMember();
        message.setTo(coach.getUserEmail());
        message.setSubject("Réservation Privée de Coach par un Membre");
        message.setFrom(emailProperties.getFrom());
        message.setText(
                "Bonjour " + coach.getUserFirstName() + " " + coach.getUserLastName() + ",\n\n"
                        + "Un membre a réservé une séance privée.\n\n"
                        + "Membre: " + member.getUserFirstName() + " " + member.getUserLastName() + "\n"
                        + "Date: " + new SimpleDateFormat("dd/MM/yyyy").format(privateSession.getPrivateSessionStartDateTime()) + "\n"
                        + "Heure: " + new SimpleDateFormat("HH:mm").format(privateSession.getPrivateSessionStartDateTime())
                        + " - " + new SimpleDateFormat("HH:mm").format(privateSession.getPrivateSessionEndDateTime()) + "\n\n"
                        + "Cordialement,\n" + emailProperties.getBrandName());
        sendEmail(message);
    }

    @Override
    public void sendCancelPrivateSessionEmail(PrivateSession privateSession)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        Coach coach = privateSession.getPrivateSessionCoach();
        Member member = privateSession.getPrivateSessionMember();
        message.setTo(member.getUserEmail());
        message.setSubject("Annulation de votre Session Privée");
        message.setFrom(emailProperties.getFrom());
        message.setText(
                "Bonjour " + member.getUserFirstName() + " " + member.getUserLastName() + ",\n\n"
                        + "Votre session privée avec " + coach.getUserFirstName() + " " + coach.getUserLastName()
                        + " le " + new SimpleDateFormat("dd/MM/yyyy").format(privateSession.getPrivateSessionStartDateTime())
                        + " a été annulée.\n\n"
                        + "Cordialement,\n" + emailProperties.getBrandName());
        sendEmail(message);
    }

    @Override
    public void sendCancelSubscriptionEmail(Subscription subscription)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        Member member = subscription.getMember();
        message.setTo(member.getUserEmail());
        message.setSubject("Suppression de votre abonnement");
        message.setFrom(emailProperties.getFrom());
        message.setText(
                "Cher/Chère " + member.getUserFirstName() + " " + member.getUserLastName() + ",\n\n"
                        + "Votre abonnement pour " + subscription.getSubscriptionActivity().getActName()
                        + " a été supprimé.\n\n"
                        + "Cordialement,\n" + emailProperties.getBrandName());
        sendEmail(message);
    }

    @Override
    public void sendConfirmationSubscriptionEmail(Subscription subscription)
    {
        Member member = subscription.getMember();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String activityName = subscription.getSubscriptionOffer().getOfferActivity().getActName() != null
                ? subscription.getSubscriptionOffer().getOfferActivity().getActName()
                : "Activité";

        EmailTemplateVariables variables = EmailTemplateVariables.builder()
                .userName(buildMemberDisplayName(member))
                .actionUrl(frontendProperties.getLoginUrl())
                .appName(emailProperties.getBrandName())
                .supportEmail(emailProperties.getSupport())
                .year(String.valueOf(Year.now().getValue()))
                .headline("Abonnement confirmé")
                .introText("Nous avons le plaisir de vous confirmer votre adhésion chez "
                        + emailProperties.getBrandName() + ". Voici le récapitulatif de votre abonnement.")
                .footerText("Merci de votre confiance. L'équipe " + emailProperties.getBrandName())
                .ctaLabel("ACCEDER A MON ESPACE")
                .activityName(activityName)
                .startDate(subscription.getSubscriptionStartDate().format(formatter))
                .endDate(subscription.getSubscriptionEndDate().format(formatter))
                .subscriptionPrice(String.format("%.3f TND", subscription.getSubscriptionPrice()))
                .build();

        sendHtmlEmail(
                member.getUserEmail(),
                "Confirmation de votre adhésion - " + emailProperties.getBrandName(),
                templateBuilder.buildSubscriptionConfirmationEmail(variables)
        );
    }

    private String buildMemberDisplayName(Member member)
    {
        String firstName = member.getUserFirstName() == null ? "" : member.getUserFirstName().trim();
        String lastName = member.getUserLastName() == null ? "" : member.getUserLastName().trim();
        String fullName = (firstName + " " + lastName).trim();
        return fullName.isEmpty() ? "Membre" : fullName;
    }

    @Override
    public void sendCancelSessionEmail(Session session)
    {
        Coach coach = session.getSessionCoach();
        session.getSessionMembers().forEach(member -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(member.getUserEmail());
            message.setSubject("Annulation de session");
            message.setFrom(emailProperties.getFrom());
            message.setText(
                    "Bonjour " + member.getUserFirstName() + " " + member.getUserLastName() + ",\n\n"
                            + "La session avec " + coach.getUserFirstName() + " " + coach.getUserLastName()
                            + " le " + new SimpleDateFormat("dd/MM/yyyy").format(session.getSessionDate())
                            + " a été annulée.\n\n"
                            + "Cordialement,\n" + emailProperties.getBrandName());
            sendEmail(message);
        });
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent)
    {
        try
        {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(emailProperties.getFrom());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
        }
        catch (MessagingException exception)
        {
            throw new RuntimeException("Failed to send email to " + to, exception);
        }
    }

    private String buildDisplayName(User user)
    {
        String firstName = user.getUserFirstName() == null ? "" : user.getUserFirstName().trim();
        String lastName = user.getUserLastName() == null ? "" : user.getUserLastName().trim();
        String fullName = (firstName + " " + lastName).trim();
        return fullName.isEmpty() ? "Utilisateur" : fullName;
    }
}
