package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.BookingManagementModal;
import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.BookingModal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    @FXML
    private Button adminPageButton;

    @FXML
    private void handleGoOnAdminPage() {
        try {
            // Charger le fichier FXML de la page d'inscription
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ynov/m2/advanced_software_development/cautious_guacamole/client/page/admin-page.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle et définir la nouvelle scène
            Stage stage = (Stage) adminPageButton.getScene().getWindow();
            stage.setTitle("Admin");
            stage.setScene(new Scene(root, 1525, 775));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBooking() {
        // Créer l'instance de la modal
        BookingModal modal = new BookingModal();

        // Passer la référence de la modal au contrôleur
        modal.show();  // Afficher la modal
    }

    @FXML
    public void handleManageBooking() {
        // Créer l'instance de la modal
        BookingManagementModal modal = new BookingManagementModal();

        // Passer la référence de la modal au contrôleur
        modal.show();  // Afficher la modal
    }
}
