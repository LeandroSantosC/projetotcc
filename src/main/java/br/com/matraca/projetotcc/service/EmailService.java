package br.com.matraca.projetotcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String token) {
        String baseUrl = "http://localhost:8080"; // URL base da sua aplicação

        String link = baseUrl + "/auth/verify-email?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Confirme seu e-mail");
        message.setText("Clique no link para confirmar seu e-mail: " + link);

        mailSender.send(message);
    }
}
