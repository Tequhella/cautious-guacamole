package com.ynov.m2.advanced_software_development.cautious_guacamole.server.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private NotificationService notificationService;

    private String destinataire;
    private String sujet;
    private String contenu;

    @BeforeEach
    void setUp() {
        destinataire = "bob@example.com";
        sujet = "Test de notification";
        contenu = "Bonjour Bob, ceci est un test.";
    }

    @Test
    void testEnvoyerNotification_Success() {
        // Arrange
        // On s'assure que l'appel à mailSender.send(...) ne lève pas d'exception
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        notificationService.envoyerNotification(destinataire, sujet, contenu);

        // Assert
        // Vérifie que mailSender.send(...) est appelé une fois
        // et capture l'argument pour vérifier son contenu
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());
        
        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage, "Le message envoyé ne doit pas être null");
        assertArrayEquals(new String[]{destinataire}, sentMessage.getTo());
        assertEquals(sujet, sentMessage.getSubject());
        assertEquals(contenu, sentMessage.getText());
    }

    @Test
    void testEnvoyerNotification_Exception() {
        // Arrange
        // Simule une exception lors de l'envoi
        doThrow(new RuntimeException("SMTP error")).when(mailSender).send(any(SimpleMailMessage.class));

        // Act
        // On vérifie que l'appel ne lève pas d'exception côté service
        // (l'exception est gérée dans un bloc try/catch)
        assertDoesNotThrow(() ->
                notificationService.envoyerNotification(destinataire, sujet, contenu)
        );

        // Assert
        // On vérifie simplement que mailSender.send(...) a bien été appelé
        // et qu'aucune exception n'est ressortie
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
