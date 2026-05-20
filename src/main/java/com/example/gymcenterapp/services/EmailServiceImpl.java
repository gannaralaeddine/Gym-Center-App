package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Coach;
import com.example.gymcenterapp.entities.ConfirmationToken;
import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.entities.PrivateSession;
import com.example.gymcenterapp.entities.Session;
import com.example.gymcenterapp.entities.Subscription;
import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.repositories.ConfirmationTokenRepository;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl
{
    @Value("${app.email}")
    private String appEmail;

    @Value("${app.API}")
    private String appAPI;
    private JavaMailSender javaMailSender;
    @Autowired
    private JavaMailSender mailSender;
    private ConfirmationTokenRepository confirmationTokenRepository;

    public EmailServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Autowired
    public void EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }


    public ConfirmationToken sendConfirmationEmail(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        String confirmationLink = appAPI + "/gym-center/user/confirm-account?token=" + confirmationToken.getConfirmationToken();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String htmlContent = buildHtmlEmail(user, confirmationLink);


            helper.setTo(user.getUserEmail());
            helper.setSubject("Confirmez votre compte - Gym Center");
            helper.setText(htmlContent, true);   // true = HTML

            mailSender.send(message);
        }
        catch (MessagingException e)
        {
            throw new RuntimeException("Failed to send email", e);
        }


        System.out.println("Registration successful !");

        return confirmationTokenRepository.save(confirmationToken);
    }

    private String buildHtmlEmail(User user, String confirmationLink) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>Confirmez votre compte</title>" +
                "    <style>" +
                "        body {font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px;}" +
                "        .container {max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.1);}" +
                "        .header {background: linear-gradient(135deg, #1e3a8a, #3b82f6); color: white; padding: 30px; text-align: center;}" +
                "        .content {padding: 40px; color: #333333; line-height: 1.7;}" +
                "        .button {display: inline-block; background-color: #2563eb; color: white; padding: 14px 32px; text-decoration: none; border-radius: 8px; font-weight: bold; margin: 25px 0; font-size: 16px;}" +
                "        .footer {background-color: #f8fafc; padding: 25px; text-align: center; color: #64748b; font-size: 14px; border-top: 1px solid #e2e8f0;}" +
                "        .highlight {color: #1e40af; font-weight: 600;}" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"container\">" +
                "        <div class=\"header\">" +
                "            <h1>Gym Center</h1>" +
                "        </div>" +
                "        <div class=\"content\">" +
                "            <p style=\"font-size: 18px;\">Bonjour <strong>" + user.getUserFirstName() + " " + user.getUserLastName() + "</strong>,</p>" +
                "            <p>Merci d’avoir choisi <span class=\"highlight\">Gym Center</span> !</p>" +
                "            <p>Pour assurer le plus haut niveau de sécurité, nous devons vérifier votre identité.</p>" +
                "            <p style=\"text-align: center; margin: 35px 0;\">" +
                "                <a href=\"" + confirmationLink + "\" class=\"button\">VÉRIFIER MON COMPTE</a>" +
                "            </p>" +
                "            <p>Une fois vérifié, vous aurez accès à toutes les fonctionnalités.</p>" +
                "            <p>Si vous avez besoin d’aide, contactez-nous :</p>" +
                "            <p>📧 gannarala@gmail.com<br>📞 +216 25 944 019</p>" +
                "        </div>" +
                "        <div class=\"footer\">" +
                "            <p><strong>Cordialement,<br>L'équipe Gym Center</strong></p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }


    public void sendCoachBookingNotificationEmail(PrivateSession privateSession)
    {
        SimpleMailMessage coachBookingNotification = new SimpleMailMessage();
        Coach coach = privateSession.getPrivateSessionCoach();
        Member member = privateSession.getPrivateSessionMember();
        coachBookingNotification.setTo(coach.getUserEmail());
        coachBookingNotification.setSubject("Réservation Privée de Coach par un Membre");
        coachBookingNotification.setFrom(appEmail);
        coachBookingNotification.setText(
            "Bonjour " + coach.getUserFirstName() + " " + coach.getUserLastName() + ",\n\n"
            + "J'espère que vous allez bien.\n\n"
            + "Je vous écris pour vous informer qu'un de nos membres a réservé vos services pour une séance privée. Voici les détails de la réservation :\n\n"
            + "Nom du membre: " + member.getUserFirstName() + " " + member.getUserLastName() + "\n"
            + "Date de la séance privée: " + new SimpleDateFormat("dd/MM/yyyy").format(privateSession.getPrivateSessionStartDateTime()) +"\n"
            + "Heure de la séance privée: " + new SimpleDateFormat("HH:mm").format(privateSession.getPrivateSessionStartDateTime()) + " - " + new SimpleDateFormat("HH:mm").format(privateSession.getPrivateSessionEndDateTime()) + "\n\n"
            + "Pour plus de détails, merci de consulter la liste des séances privées dans votre profil\n\n"
            + "Merci de me faire savoir si vous avez besoin de plus d'informations ou de matériel spécifique pour cette session.\n\n"
            + "Je vous remercie pour votre attention et votre collaboration.\n\n"
            + "Cordialement,\n"
            + "Gym Center\n");

        sendEmail(coachBookingNotification);
    }

    public void sendCancelPrivateSessionEmail(PrivateSession privateSession)
    {
        SimpleMailMessage cancelPrivateSessionNotifiction = new SimpleMailMessage();
        Coach coach = privateSession.getPrivateSessionCoach();
        Member member = privateSession.getPrivateSessionMember();
        cancelPrivateSessionNotifiction.setTo(member.getUserEmail());
        cancelPrivateSessionNotifiction.setSubject("Annulation de votre Session Privée avec le Coach " + coach.getUserFirstName() + " " + coach.getUserLastName());
        cancelPrivateSessionNotifiction.setFrom(appEmail);
        cancelPrivateSessionNotifiction.setText(
            "Bonjour " + member.getUserFirstName() + " " + member.getUserLastName() + ",\n\n"
            + "J'espère que vous allez bien.\n\n"
            + "Je suis désolé de vous informer que votre session privée prévue avec Coach " + coach.getUserFirstName() + " " + coach.getUserLastName() + " le " + new SimpleDateFormat("dd/MM/yyyy").format(privateSession.getPrivateSessionStartDateTime()) + " à " + new SimpleDateFormat("HH:mm").format(privateSession.getPrivateSessionStartDateTime()) + " a été annulée en raison d'un imprévu. \n\n"
            + "Nous comprenons que cela puisse être un désagrément et nous nous excusons pour la gêne occasionnée. Nous travaillons actuellement à reprogrammer votre session et vous contacterons dès que possible pour fixer une nouvelle date et heure qui vous conviennent. \n\n"
            + "En attendant, n'hésitez pas à me contacter si vous avez des questions ou des préoccupations. Merci pour votre compréhension et votre patience.\n\n"
            + "Je vous remercie extrêmement une autre fois pour votre compréhension \n\n"
            + "Cordialement,\n"
            + "Gym Center\n");

        sendEmail(cancelPrivateSessionNotifiction);
    }

    public void sendCancelSubscriptionEmail(Subscription subscription)
    {
        SimpleMailMessage cancelPrivateSessionNotifiction = new SimpleMailMessage();
        Member member = subscription.getMember();
        cancelPrivateSessionNotifiction.setTo(member.getUserEmail());
        cancelPrivateSessionNotifiction.setSubject("Suppression de votre abonnement à la salle de sport");
        cancelPrivateSessionNotifiction.setFrom(appEmail);
        cancelPrivateSessionNotifiction.setText(
            "Cher/Chère Membre " + member.getUserFirstName() + " " + member.getUserLastName() + ",\n\n"
            + "Nous espérons que vous allez bien.\n\n"
            + "Nous souhaitons vous informer que votre abonnement pour l'activité " + subscription.getSubscriptionActivity().getActName() + "  au centre d'entraînement a été supprimé par l'administrateur.\n\nPour toute question ou information complémentaire concernant cette action, nous vous invitons à contacter l'administration du centre de gym. \n\n"
            + "Nous restons à votre disposition pour toute assistance supplémentaire. \n\n"
            + "Cordialement,\n"
            + "Gym Center\n");

        sendEmail(cancelPrivateSessionNotifiction);
    }

    public void sendConfirmationSubscriptionEmail(Subscription subscription)
    {
        SimpleMailMessage confirmationSubscriptionEmail = new SimpleMailMessage();
        Member member = subscription.getMember();
        confirmationSubscriptionEmail.setTo(member.getUserEmail());
        confirmationSubscriptionEmail.setSubject("Confirmation de votre adhésion chez notre Gym Center");
        confirmationSubscriptionEmail.setFrom(appEmail);
        confirmationSubscriptionEmail.setText(
            "Cher/Chère Membre " + member.getUserFirstName() + " " + member.getUserLastName() + ",\n\n"
            + "Nous avons le plaisir de vous informer que votre adhésion chez nous a été confirmée avec succès. Nous vous remercions d’avoir choisi notre centre et nous sommes ravis de vous accueillir parmi nos membres.\n\n"
            + "Votre abonnement pour l'activité " + subscription.getSubscriptionActivity().getActName() + " a été créé le " + new SimpleDateFormat("dd/MM/yyyy").format(subscription.getSubscriptionStartDate()) + " jusqu'à " + new SimpleDateFormat("dd/MM/yyyy").format(subscription.getSubscriptionEndDate())+ " et vous pouvez désormais, durant cette période, profiter de toutes nos installations et service.\n\n"
            + "Nous restons à votre disposition pour toute assistance supplémentaire. \n\n"
            + "Cordialement,\n"
            + "Gym Center\n");

        sendEmail(confirmationSubscriptionEmail);
    }

    public void sendCancelSessionEmail(Session session)
    {
        SimpleMailMessage cancelPrivateSessionNotifiction = new SimpleMailMessage();
        Coach coach = session.getSessionCoach();
        session.getSessionMembers().forEach((Member member) -> {
            cancelPrivateSessionNotifiction.setTo(member.getUserEmail());
            cancelPrivateSessionNotifiction.setSubject("Annulation de la Session avec le Coach " + coach.getUserFirstName() + " " + coach.getUserLastName());
            cancelPrivateSessionNotifiction.setFrom(appEmail);
            cancelPrivateSessionNotifiction.setText(
                "Bonjour " + member.getUserFirstName() + " " + member.getUserLastName() + ",\n\n"
                + "J'espère que vous allez bien.\n\n"
                + "Je suis désolé de vous informer que la session prévue avec Coach " + coach.getUserFirstName() + " " + coach.getUserLastName() + " le " + new SimpleDateFormat("dd/MM/yyyy").format(session.getSessionDate()) + " à " + new SimpleDateFormat("HH:mm").format(session.getSessionDate()) + " a été annulée en raison d'un imprévu. \n\n"
                + "Nous comprenons que cela puisse être un désagrément et nous nous excusons pour la gêne occasionnée. Nous travaillons actuellement à reprogrammer une nouvelle session dès que possible.\n\n"
                + "En attendant, n'hésitez pas à me contacter si vous avez des questions ou des préoccupations. Merci pour votre compréhension et votre patience.\n\n"
                + "Je vous remercie extrêmement une autre fois pour votre compréhension \n\n"
                + "Cordialement,\n"
                + "Gym Center\n");
    
            sendEmail(cancelPrivateSessionNotifiction);
        });
    }

}
