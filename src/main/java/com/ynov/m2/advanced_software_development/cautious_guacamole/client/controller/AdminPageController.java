package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.RoomModal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPageController {
    @FXML
    private Button homePageButton;

    @FXML
    private void handleGoOnHomePage() {
        try {
            // Charger le fichier FXML de la page d'inscription
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ynov/m2/advanced_software_development/cautious_guacamole/client/page/home-page.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle et définir la nouvelle scène
            Stage stage = (Stage) homePageButton.getScene().getWindow();
            stage.setTitle("Accueil");
            stage.setScene(new Scene(root, 1525, 775));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRoom() {
        // Créer l'instance de la modal
        RoomModal modal = new RoomModal();

        // Passer la référence de la modal au contrôleur
        modal.show();  // Afficher la modal
    }
}
