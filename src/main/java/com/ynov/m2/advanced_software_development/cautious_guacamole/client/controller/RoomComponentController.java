package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.ManageRoomModal;
import javafx.fxml.FXML;

public class RoomComponentController {
    @FXML
    public void handleManageRoom() {
        // Créer l'instance de la modal
        ManageRoomModal modal = new ManageRoomModal();

        // Passer la référence de la modal au contrôleur
        modal.show();  // Afficher la modal
    }
}
