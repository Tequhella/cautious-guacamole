package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpPageController {
    @FXML
    private Button alreadyHaveAccountButton;

    @FXML
    private void handleHaveAccount() {
        try {
            // Charger le fichier FXML de la page d'inscription
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ynov/m2/advanced_software_development/cautious_guacamole/client/page/login-page.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle et définir la nouvelle scène
            Stage stage = (Stage) alreadyHaveAccountButton.getScene().getWindow();
            stage.setTitle("Connexion");
            stage.setScene(new Scene(root, 1525, 775));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
