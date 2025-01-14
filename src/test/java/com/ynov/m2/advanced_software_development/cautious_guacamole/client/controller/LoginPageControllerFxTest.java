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

public class LoginPageControllerFxTest extends ApplicationTest {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        // Charge le FXML "login-page.fxml" (hypothèse : c'est la page qui a fx:controller="LoginPageController")
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/ynov/m2/advanced_software_development/cautious_guacamole/client/page/login-page.fxml"));
        Parent root = loader.load();
        
        // Met le root dans la scène du stage
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Login Page");
        stage.show();

        // On mémorise le stage pour les vérifications
        this.primaryStage = stage;
    }

    @Test
    @DisplayName("Cliquer sur createAccountButton => charge sign-up-page.fxml")
    void testHandleCreateAccount() {
        // Récupérer le bouton (fx:id="createAccountButton")
        Button createAccountButton = lookup("#createAccountButton").queryButton();
        assertNotNull(createAccountButton, "Le bouton createAccountButton doit exister dans le FXML.");

        // Cliquer
        clickOn(createAccountButton);
        waitForFxEvents();

        // Vérifier que le titre du stage est devenu "Inscription"
        assertEquals("Inscription", primaryStage.getTitle());
        // Vérifier la taille indiquée dans handleCreateAccount()
        assertEquals(1525, primaryStage.getScene().getWidth(), 1.0);
        assertEquals(775, primaryStage.getScene().getHeight(), 1.0);
    }

    @Test
    @DisplayName("Cliquer sur le bouton handleForgotPassword => ouvre la ForgotPasswordModal")
    void testHandleForgotPassword() {
        // Supposez que vous avez un bouton fx:id="forgotPasswordButton" (ou un ID similaire)
        Button forgotPasswordButton = lookup("#forgotPasswordButton").queryButton();
        assertNotNull(forgotPasswordButton, "Le bouton forgotPasswordButton doit exister dans le FXML.");

        // Cliquer
        clickOn(forgotPasswordButton);
        waitForFxEvents();

        // handleForgotPassword() fait un `new ForgotPasswordModal()` puis .show()
        // Cela ouvre un nouveau stage (modal).
        Stage modalStage = getSecondStage();
        assertNotNull(modalStage, "La ForgotPasswordModal devrait s'ouvrir dans un nouveau Stage.");
        assertTrue(modalStage.isShowing(), "Le stage de la modal doit être affiché.");

        // Optionnel : vérifier le titre de la modal si ForgotPasswordModal le définit
        // assertEquals("Reset Password", modalStage.getTitle());
    }

    // Méthode utilitaire pour récupérer le second stage (celui qui n'est pas primaryStage).
    private Stage getSecondStage() {
        return listTargetWindows().stream()
                .filter(window -> window instanceof Stage)
                .map(window -> (Stage) window)
                .filter(s -> s != primaryStage)
                .findFirst()
                .orElse(null);
    }
}
