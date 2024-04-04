package com.esprit.spring.ftthback.services;

import com.esprit.spring.ftthback.models.PasswordResetToken;
import com.esprit.spring.ftthback.models.User;
import com.esprit.spring.ftthback.repository.PasswordResetTokenRepository;
import com.esprit.spring.ftthback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
    @Autowired
    public JavaMailSender emailSender;

    public PasswordResetToken createPasswordResetToken(String email) throws MessagingException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String token = generateToken();
            PasswordResetToken resetToken = new PasswordResetToken(token, user);
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            Date tomorrowDate = Date.from(tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant());
            resetToken.setExpiryDate(tomorrowDate);
            passwordResetTokenRepository.save(resetToken);

            // Envoi de l'e-mail avec le lien de réinitialisation
            sendPasswordResetEmail(email, token);

            return resetToken;
        } else {
            return null;
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

    public void sendPasswordResetEmail(String email, String token) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        String resetUrl = "http://localhost:4200/authentication/reset-pwd/" + token;

        String htmlMsg = "<h3>Bonjour,</h3>"
                + "<p>Veuillez cliquer sur le lien ci-dessous pour réinitialiser votre mot de passe :</p>"
                + "<p><a href=\"" + resetUrl + "\">Réinitialiser le mot de passe</a></p>"
                + "<p>Ce lien expirera dans 24 heures.</p>"
                + "<p>Cordialement,</p>";

        message.setContent(htmlMsg, "text/html");
        helper.setTo(email);
        helper.setSubject("Réinitialisation du mot de passe");
        this.emailSender.send(message);
    }


}

