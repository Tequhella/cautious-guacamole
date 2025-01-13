package com.ynov.m2.advanced_software_development.cautious_guacamole.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void envoyerNotification(String destinataire, String sujet, String contenu) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(destinataire);
            message.setSubject(sujet);
            message.setText(contenu);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi d'email : " + e.getMessage());
        }
    }

}
