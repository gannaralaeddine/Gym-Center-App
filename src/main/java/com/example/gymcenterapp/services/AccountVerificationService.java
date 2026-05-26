package com.example.gymcenterapp.services;

import com.example.gymcenterapp.email.config.AppFrontendProperties;
import com.example.gymcenterapp.email.page.VerificationPageRenderer;
import com.example.gymcenterapp.entities.ConfirmationToken;
import com.example.gymcenterapp.entities.User;
import com.example.gymcenterapp.repositories.ConfirmationTokenRepository;
import com.example.gymcenterapp.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class AccountVerificationService
{
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;
    private final VerificationPageRenderer pageRenderer;
    private final AppFrontendProperties frontendProperties;

    public AccountVerificationService(
            ConfirmationTokenRepository confirmationTokenRepository,
            UserRepository userRepository,
            VerificationPageRenderer pageRenderer,
            AppFrontendProperties frontendProperties)
    {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.userRepository = userRepository;
        this.pageRenderer = pageRenderer;
        this.frontendProperties = frontendProperties;
    }

    public ResponseEntity<?> confirmAccount(String confirmationToken, boolean immediateRedirect)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_HTML)
                    .body(pageRenderer.renderError());
        }

        User user = userRepository.findByEmail(token.getUser().getUserEmail());
        user.setUserIsEnabled(true);
        userRepository.save(user);

        if (immediateRedirect)
        {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(frontendProperties.getLoginUrl()))
                    .build();
        }

        String displayName = buildDisplayName(user);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(pageRenderer.renderSuccess(displayName));
    }

    private String buildDisplayName(User user)
    {
        String firstName = user.getUserFirstName() == null ? "" : user.getUserFirstName().trim();
        String lastName = user.getUserLastName() == null ? "" : user.getUserLastName().trim();
        String fullName = (firstName + " " + lastName).trim();
        return fullName.isEmpty() ? "" : fullName;
    }
}
