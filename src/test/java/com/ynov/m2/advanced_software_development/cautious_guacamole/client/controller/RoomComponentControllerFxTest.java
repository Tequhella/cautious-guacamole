package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class RoomComponentControllerFxTest extends ApplicationTest {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        // Supposez que vous avez un fichier FXML "room-component.fxml"
        // qui référence RoomComponentController en fx:controller.
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
            "/com/ynov/m2/advanced_software_development/cautious_guacamole/client/component/room-component.fxml"));
        Parent root = loader.load();
        
        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Test Room Component");
        stage.show();

        this.primaryStage = stage;
    }

    @Test
    void testHandleManageRoom_OpensModal() {
        // On cherche le bouton
        Button manageRoomButton = lookup("#manageRoomButton").queryButton();
        assertNotNull(manageRoomButton, "Le bouton manageRoomButton doit exister dans le FXML.");

        // On clique
        clickOn(manageRoomButton);
        waitForFxEvents();

        // handleManageRoom() fait "new ManageRoomModal()" + "modal.show()",
        // ce qui ouvre un nouveau Stage. On le récupère.
        Stage modalStage = getSecondStage();
        assertNotNull(modalStage, "Le nouveau Stage (ManageRoomModal) doit être ouvert.");
        assertTrue(modalStage.isShowing(), "La modal doit être affichée.");
    }

    // Méthode utilitaire pour récupérer un stage autre que primaryStage
    private Stage getSecondStage() {
        return listTargetWindows().stream()
            .filter(window -> window instanceof Stage)
            .map(window -> (Stage) window)
            .filter(s -> s != primaryStage)
            .findFirst()
            .orElse(null);
    }
}
