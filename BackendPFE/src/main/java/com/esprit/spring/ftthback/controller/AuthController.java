package com.esprit.spring.ftthback.controller;

import com.esprit.spring.ftthback.jwt.JwtUtils;
import com.esprit.spring.ftthback.models.Role;
import com.esprit.spring.ftthback.models.User;
import com.esprit.spring.ftthback.payload.JwtResponse;
import com.esprit.spring.ftthback.payload.LoginRequest;
import com.esprit.spring.ftthback.payload.MessageResponse;
import com.esprit.spring.ftthback.payload.SignupRequest;
import com.esprit.spring.ftthback.repository.RoleRepository;
import com.esprit.spring.ftthback.repository.UserRepository;
import com.esprit.spring.ftthback.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    JavaMailSender emailSender;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtils.generateJwtToken(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                if (userDetails.isEnabled()){
                List<String> roles = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());

                return ResponseEntity.ok(new JwtResponse(jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));

                }else{
                    return ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(new MessageResponse("Erreur d'authentification : This user is not enable to login contact admin."));
                }
            } catch (BadCredentialsException e) {
                // Gérer l'erreur lorsque les informations d'identification sont incorrectes
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse("Erreur d'authentification : identifiant ou mot de passe incorrect."));
            } catch (AuthenticationException e) {
                // Gérer d'autres erreurs d'authentification
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new MessageResponse("Erreur d'authentification : " + e.getMessage()));
            }
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur : le nom d'utilisateur est déjà pris !"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur : l'e-mail est déjà utilisé !"));
        }
        // Create new user's account
        User user = new User( signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getNumtel(),
                signUpRequest.getRefnv(),
                signUpRequest.getGrade(),
                signUpRequest.getStatus(),
        signUpRequest.getEmail());
        List<String> strRoles = signUpRequest.getRole();
        List<Role> roles = new ArrayList();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "responsable":
                        Role managerRole = roleRepository.findByName("ROLE_RESPONSABLE")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(managerRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName("ROLE_USER")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setEnable(false);

        userRepository.save(user);
        sendCodeVerification(user.getEmail(), user.getCode_verification());

        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès !"));
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam Long code) {
        User user = userRepository.findByCodeVerification(code);
        if (user != null){
            user.setEnable(true);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Account verified !"));
        }else {
            return ResponseEntity.ok(new MessageResponse("Incorrect code !"));
        }
    }
    public void sendCodeVerification(String email, Long code_verification) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");


        String htmlMsg = "<h3>Bonjour,</h3>"
                + "<p>Your verification code is:\n" +
                "\n" + code_verification +
                "\n" +
                "Your account can’t be accessed without this verification code, even if you didn’t submit this request.</p>"
                +   "<p>Cordialement,</p>";

        message.setContent(htmlMsg, "text/html");
        helper.setTo(email);
        helper.setSubject("verification code");
        this.emailSender.send(message);
    }

}
