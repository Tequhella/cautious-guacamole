package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.ForgotPasswordModal;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForgotPasswordModalControllerTest {

    @Mock
    private ForgotPasswordModal mockModal;

    private ForgotPasswordModalController controller;

    // Contrôles JavaFX "factices" (non liés à un vrai moteur JavaFX)
    private TextField emailField;
    private Button sendButton;
    private Button cancelButton;

    @BeforeEach
    void setUp() throws Exception {
        // 1) Instanciation du contrôleur
        controller = new ForgotPasswordModalController();
        // On injecte la modal mockée
        controller.setModal(mockModal);

        // 2) Instanciation des contrôles JavaFX
        emailField = new TextField();
        sendButton = new Button("Envoyer");
        cancelButton = new Button("Annuler");

        // 3) Injection via réflexion (car @FXML)
        setPrivateField(controller, "emailField", emailField);
        setPrivateField(controller, "sendButton", sendButton);
        setPrivateField(controller, "cancelButton", cancelButton);
    }

    @Test
    void testHandleSend() {
        // Arrange
        emailField.setText("test@example.com");

        // Act
        // On appelle directement la méthode handleSend()
        controller.handleSend();

        // Assert
        // 1) Vérifie que la modal est fermée
        verify(mockModal, times(1)).close();

        // 2) Vérifie (optionnel) que l'email a été récupéré
        //    Le code actuel fait un System.out.println("Email envoyé à: " + email);
        //    Pour tester le println, on devrait rediriger System.out (plus complexe).
        //    Ici, on se contente de valider la logique de base (close).
    }

    @Test
    void testHandleCancel() {
        // Act
        controller.handleCancel();

        // Assert
        verify(mockModal, times(1)).close();
    }

    // ----------------------------------------------------------------------
    // Méthode utilitaire pour injecter un champ privé "à la main"
    // ----------------------------------------------------------------------
    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}

