package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.BookingManagementModal;
import javafx.fxml.FXML;

public class BookingManagementModalController {private BookingManagementModal modal;  // Référence à la modal

    // Méthode pour recevoir la référence de la modal
    public void setModal(BookingManagementModal modal) {
        this.modal = modal;
    }

    // Action au clic sur le bouton "Annuler"
    @FXML void handleCancel() {
        modal.close();  // Ferme simplement la modal
    }

}
