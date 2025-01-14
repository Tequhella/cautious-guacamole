package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.ForgotPasswordModal;

public class ForgotPasswordModalController {

    @FXML
    private TextField emailField;  // Champ pour entrer l'email
    @FXML
    private Button sendButton;    // Bouton pour envoyer
    @FXML
    private Button cancelButton;  // Bouton pour annuler

    private ForgotPasswordModal modal;  // Référence à la modal

    // Méthode pour recevoir la référence de la modal
    public void setModal(ForgotPasswordModal modal) {
        this.modal = modal;
    }

    // Action au clic sur le bouton "Envoyer"
    @FXML
    private void handleSend() {
        String email = emailField.getText();
        // Ici vous pouvez ajouter la logique pour envoyer l'email (ex. vérification, envoi, etc.)
        System.out.println("Email envoyé à: " + email);  // Exemple d'action à faire
        modal.close();  // Ferme la modal après l'envoi
    }

    // Action au clic sur le bouton "Annuler"
    @FXML
    private void handleCancel() {
        modal.close();  // Ferme simplement la modal
    }
}