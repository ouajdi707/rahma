package com.esprit.spring.ftthback.controller;


import com.esprit.spring.ftthback.services.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/request")
    public ResponseEntity<?> requestPasswordReset(@RequestParam String email) {
        System.out.println("Email  " + email);
        return ResponseEntity.ok(passwordResetService.createPasswordResetToken(email)) ;
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        passwordResetService.resetPassword(token, newPassword);
        return ResponseEntity.ok().build();
    }

}
