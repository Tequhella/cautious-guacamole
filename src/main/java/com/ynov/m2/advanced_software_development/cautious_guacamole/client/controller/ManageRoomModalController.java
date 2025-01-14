package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.ManageRoomModal;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

public class ManageRoomModalController {
    private ManageRoomModal modal;  // Référence à la modal

    // Méthode pour recevoir la référence de la modal
    public void setModal(ManageRoomModal modal) {
        this.modal = modal;
    }

    // Action au clic sur le bouton "Annuler"
    @FXML void handleCancel() {
        modal.close();  // Ferme simplement la modal
    }

    @FXML
    private TextField seatingCapacityField;

    @FXML
    private CheckComboBox<String> equipmentDropdown;

    public void initialize() {
        equipmentDropdown.getItems().addAll("equipement 1", "equipement 2", "equipement 3");

        seatingCapacityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                seatingCapacityField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
