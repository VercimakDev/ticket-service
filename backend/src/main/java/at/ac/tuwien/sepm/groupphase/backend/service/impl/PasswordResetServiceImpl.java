package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.PasswordResetToken;
import at.ac.tuwien.sepm.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TokenRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;


    @Autowired
    private EmailServiceImpl emailService;

    //TODO: Store the token in the database
    @Autowired
    private TokenRepository tokenRepository;

    public void initiatePasswordReset(String email) {

        String token = generateToken();

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setEmail(email);
        passwordResetToken.setToken(token);
        //This token expires 15minutes after creation
        passwordResetToken.setExpirationTime(LocalDateTime.now().plusMinutes(15));

        tokenRepository.save(passwordResetToken);

        String passwordResetLink = "http://localhost:4200/#/reset-password?token=" + token;
        emailService.sendPasswordResetMail(email, passwordResetLink, token);
    }


    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}