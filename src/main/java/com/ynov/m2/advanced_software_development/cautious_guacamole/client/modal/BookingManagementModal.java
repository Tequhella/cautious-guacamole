package com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller.BookingManagementModalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BookingManagementModal {
    private Stage stage;

    public BookingManagementModal() {
        try {
            // Charger le fichier FXML de la modal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("booking-management-modal.fxml"));
            Parent root = loader.load();

            BookingManagementModalController controller = loader.getController();
            controller.setModal(this);

            // Créer la scène de la modal
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Modal bloque l'interaction avec la fenêtre principale
            stage.setTitle("gestion de la réservation");

            // Appliquer le mode sans bordure (sans barre de titre)
            stage.initStyle(javafx.stage.StageStyle.UNDECORATED); // On utilise StageStyle.UTILITY pour un style sans bordure mais toujours avec possibilité de minimiser.

            // Créer et définir la scène avec le root du FXML
            Scene scene = new Scene(root, 450, 250);  // Largeur: 500px, Hauteur: 300px
            stage.setScene(scene);

            // Calculer la position pour centrer la fenêtre modale sur l'écran
            stage.setOnShown(e -> {
                double x = (stage.getOwner() != null) ? stage.getOwner().getX() + stage.getOwner().getWidth() / 2 - stage.getWidth() / 2 :
                        (stage.getOwner() != null) ? stage.getOwner().getX() :
                                (javafx.stage.Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth()) / 2;
                double y = (stage.getOwner() != null) ? stage.getOwner().getY() + stage.getOwner().getHeight() / 2 - stage.getHeight() / 2 :
                        (javafx.stage.Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight()) / 2;
                stage.setX(x);
                stage.setY(y);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Afficher la modal
    public void show() {
        if (stage != null) {
            stage.showAndWait();  // Montre la fenêtre modale et bloque la fenêtre principale
        } else {
            System.err.println("Erreur : Le stage de la modale est null.");
        }
    }

    // Fermer la modal
    public void close() {
        if (stage != null) {
            stage.close();  // Ferme la fenêtre modale
        } else {
            System.err.println("Erreur : Le stage de la modale est null.");
        }
    }
}
