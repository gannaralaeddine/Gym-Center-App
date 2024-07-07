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
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl
{
    @Value("${app.email}")
    private String appEmail;

    @Value("${app.API}")
    private String appAPI;
    private JavaMailSender javaMailSender;
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


    public ConfirmationToken sendConfirmationEmail(User user)
    {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        SimpleMailMessage RegistrationConfirmation = new SimpleMailMessage();
        RegistrationConfirmation.setTo(user.getUserEmail());
        RegistrationConfirmation.setSubject("Votre compte est prêt");
        RegistrationConfirmation.setFrom(appEmail);
        RegistrationConfirmation.setText("Votre compte a été créé avec succès !");
        sendEmail(RegistrationConfirmation);


        SimpleMailMessage accountValidation = new SimpleMailMessage();
        accountValidation.setTo(user.getUserEmail());
        accountValidation.setSubject("Confirmer votre inscription");
        accountValidation.setFrom(appEmail);

        accountValidation.setText(
                "Hey " + user.getUserFirstName() + " " + user.getUserLastName() +
                        "\n" +
                        "Merci d’avoir choisi Gym Center, Pour assurer le plus haut niveau de sécurité et de confiance, nous avons besoin d’un processus de vérification d’identité unique. Cette étape aide à vous protéger, vous et notre communauté, contre les accès non autorisés et les activités frauduleuses.\n" +
                        "Nous comprenons la sensibilité de vos informations, c’est pourquoi notre processus de vérification d’identité est conçu pour être sécurisé et confidentiel.\n" +
                        "\n" +
                        "Pour valider votre compte, veuillez cliquer sur le lien ci-dessous:\n" +
                        appAPI + "/gym-center/user/confirm-account?token=" + confirmationToken.getConfirmationToken() +
                        "\n" +
                        "\n" +
                        "Une fois votre identité vérifiée, vous aurez accès à toutes les fonctionnalités de la plateforme Gym Center, tout en contribuant à un environnement numérique plus sûr pour tous les utilisateurs.\n" +
                        "\n" +
                        "Si vous avez des questions ou avez besoin d’aide pendant ce processus, notre équipe d’assistance est là pour vous aider. Contactez-nous à gannarala@gmail.com ou au +216 25 944 019.\n" +
                        "\n" +
                        "Cordialement,\n" +
                        "Gym Center"
        );
        sendEmail(accountValidation);

        System.out.println("Registration successful !");

        return confirmationTokenRepository.save(confirmationToken);
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
            "Bonjour Coach " + coach.getUserFirstName() + " " + coach.getUserLastName() + ",\n\n"
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
                + "Je suis désolé de vous informer que la session prévue avec Coach " + coach.getUserFirstName() + " " + coach.getUserLastName() + " le " + new SimpleDateFormat("dd/MM/yyyy").format(session.getSessionDeadline()) + " à " + new SimpleDateFormat("HH:mm").format(session.getSessionDeadline()) + " a été annulée en raison d'un imprévu. \n\n"
                + "Nous comprenons que cela puisse être un désagrément et nous nous excusons pour la gêne occasionnée. Nous travaillons actuellement à reprogrammer une nouvelle session dès que possible.\n\n"
                + "En attendant, n'hésitez pas à me contacter si vous avez des questions ou des préoccupations. Merci pour votre compréhension et votre patience.\n\n"
                + "Je vous remercie extrêmement une autre fois pour votre compréhension \n\n"
                + "Cordialement,\n"
                + "Gym Center\n");
    
            sendEmail(cancelPrivateSessionNotifiction);
        });
    }

}
