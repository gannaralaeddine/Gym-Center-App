package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.ConfirmationToken;
import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.repositories.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl
{
    private JavaMailSender javaMailSender;
    private ConfirmationTokenRepository confirmationTokenRepository;


    @Autowired
    public void EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }


    public void sendConfirmationEmail(User user)
    {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage RegistrationConfirmation = new SimpleMailMessage();
        RegistrationConfirmation.setTo(user.getUserEmail());
        RegistrationConfirmation.setSubject("Votre compte est prêt");
        RegistrationConfirmation.setFrom("gannaralaeddine@gmail.com");
        RegistrationConfirmation.setText("Votre compte a été créé avec succès !");
        sendEmail(RegistrationConfirmation);


        SimpleMailMessage accountValidation = new SimpleMailMessage();
        accountValidation.setTo(user.getUserEmail());
        accountValidation.setSubject("Confirmer votre inscription");
        accountValidation.setFrom("gannaralaeddine@gmail.com");

        accountValidation.setText(
                "Hey " + user.getUserFirstName() + " " + user.getUserLastName() +
                        "\n" +
                        "Merci d’avoir choisi Gym Center, Pour assurer le plus haut niveau de sécurité et de confiance, nous avons besoin d’un processus de vérification d’identité unique. Cette étape aide à vous protéger, vous et notre communauté, contre les accès non autorisés et les activités frauduleuses.\n" +
                        "Nous comprenons la sensibilité de vos informations, c’est pourquoi notre processus de vérification d’identité est conçu pour être sécurisé et confidentiel.\n" +
                        "\n" +
                        "Pour valider votre compte, veuillez cliquer sur le lien ci-dessous:\n" +
                        "http://localhost:8089/gym-center/user/confirm-account?token=" + confirmationToken.getConfirmationToken() +
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
    }
}
