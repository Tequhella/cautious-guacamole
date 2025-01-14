package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.BookingModal;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import org.controlsfx.control.CheckComboBox;

public class BookingModalController {
    private BookingModal modal;  // Référence à la modal

    // Méthode pour recevoir la référence de la modal
    public void setModal(BookingModal modal) {
        this.modal = modal;
    }

    // Action au clic sur le bouton "Annuler"
    @FXML void handleCancel() {
        modal.close();  // Ferme simplement la modal
    }

    @FXML
    private CheckComboBox<String> userDropdown;

    public void initialize() {
        userDropdown.getItems().addAll("user1", "user2", "user3");

        // Écoute des changements dans les sélections
        userDropdown.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) change -> {
            System.out.println("Selected users: " + userDropdown.getCheckModel().getCheckedItems());
        });
    }
}
