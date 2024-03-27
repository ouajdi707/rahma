package com.esprit.spring.ftthback.services;

import com.esprit.spring.ftthback.models.PasswordResetToken;
import com.esprit.spring.ftthback.models.User;
import com.esprit.spring.ftthback.repository.PasswordResetTokenRepository;
import com.esprit.spring.ftthback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetToken createPasswordResetToken(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String token = generateToken();
            PasswordResetToken resetToken = new PasswordResetToken(token, user);
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            Date tomorrowDate = Date.from(tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant());
            resetToken.setExpiryDate(tomorrowDate);
            passwordResetTokenRepository.save(resetToken);
            return resetToken;// Envoyer un e-mail avec le lien de réinitialisation
        } else {
            return null;
            // Gérer le cas où l'utilisateur n'existe pas
        }
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
        if (resetToken == null || resetToken.isExpired()) {
            // Gérer le cas où le jeton est invalide ou expiré
        }
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword)); // Encoder le nouveau mot de passe
        userRepository.save(user);
        passwordResetTokenRepository.delete(resetToken);
    }


    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}

