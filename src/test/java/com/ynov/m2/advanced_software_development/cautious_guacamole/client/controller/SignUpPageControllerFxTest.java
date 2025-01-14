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

public class SignUpPageControllerFxTest extends ApplicationTest {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        // 1) Charger sign-up-page.fxml (qui doit avoir fx:controller="SignUpPageController")
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
            "/com/ynov/m2/advanced_software_development/cautious_guacamole/client/page/sign-up-page.fxml"));
        Parent root = loader.load();

        // 2) Configurer la scène
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Sign Up");
        stage.show();

        this.primaryStage = stage;
    }

    @Test
    @DisplayName("Cliquer sur alreadyHaveAccountButton => charge login-page.fxml")
    void testHandleHaveAccount() {
        // 1) Récupérer le bouton (fx:id="alreadyHaveAccountButton")
        Button alreadyHaveAccountButton = lookup("#alreadyHaveAccountButton").queryButton();
        assertNotNull(alreadyHaveAccountButton, 
                "Le bouton alreadyHaveAccountButton doit exister dans le FXML.");

        // 2) Cliquer
        clickOn(alreadyHaveAccountButton);
        waitForFxEvents();

        // 3) Vérifier que le titre du stage est devenu "Connexion"
        assertEquals("Connexion", primaryStage.getTitle());

        // Vérifier la taille indiquée dans handleHaveAccount() (1525, 775)
        assertEquals(1525, primaryStage.getScene().getWidth(), 1.0);
        assertEquals(775, primaryStage.getScene().getHeight(), 1.0);
    }
}

