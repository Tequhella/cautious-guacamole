package com.ynov.m2.advanced_software_development.cautious_guacamole.client.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOException;

public class RoomComponent extends Region {

    public RoomComponent() {
        // Charger le fichier FXML pour le composant
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("room-component.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            // Charger le composant FXML
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
