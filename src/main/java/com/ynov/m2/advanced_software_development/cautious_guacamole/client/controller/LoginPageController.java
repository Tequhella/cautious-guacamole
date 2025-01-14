package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.ForgotPasswordModal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {

    @FXML
    private Button createAccountButton;

    @FXML
    private void handleCreateAccount() {
        try {
            // Charger le fichier FXML de la page d'inscription
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ynov/m2/advanced_software_development/cautious_guacamole/client/page/sign-up-page.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle et définir la nouvelle scène
            Stage stage = (Stage) createAccountButton.getScene().getWindow();
            stage.setTitle("Inscription");
            stage.setScene(new Scene(root, 1525, 775));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleForgotPassword() {
        // Créer l'instance de la modal
        ForgotPasswordModal modal = new ForgotPasswordModal();

        // Passer la référence de la modal au contrôleur
        modal.show();  // Afficher la modal
    }
}
