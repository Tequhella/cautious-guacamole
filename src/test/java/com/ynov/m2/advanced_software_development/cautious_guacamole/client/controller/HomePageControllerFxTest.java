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

public class HomePageControllerFxTest extends ApplicationTest {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        // Charge le FXML "home-page.fxml" (celui qui a fx:controller="...HomePageController")
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/ynov/m2/advanced_software_development/cautious_guacamole/client/page/home-page.fxml"));
        Parent root = loader.load();
        
        // Met le root dans la scène du stage
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Home Page");
        stage.show();

        // On mémorise le stage pour les vérifications
        this.primaryStage = stage;
    }

    @Test
    @DisplayName("Cliquer sur adminPageButton => charge admin-page.fxml")
    void testHandleGoOnAdminPage() {
        // Récupérer le bouton
        Button adminPageButton = lookup("#adminPageButton").queryButton();
        assertNotNull(adminPageButton, "Le bouton adminPageButton doit exister dans le FXML.");

        // Cliquer
        clickOn(adminPageButton);
        waitForFxEvents();

        // Vérifier que le titre du stage est devenu "Admin"
        assertEquals("Admin", primaryStage.getTitle());
        // Vérifier la taille indiquée dans handleGoOnAdminPage()
        assertEquals(1525, primaryStage.getScene().getWidth(), 1.0);
        assertEquals(775, primaryStage.getScene().getHeight(), 1.0);
    }

    @Test
    @DisplayName("Cliquer sur le bouton handleBooking => ouvre la BookingModal")
    void testHandleBooking() {
        // Supposons que le bouton a un fx:id="bookingButton" (ou on réutilise un ID existant si c'est le cas)
        Button bookingButton = lookup("#bookingButton").queryButton();
        assertNotNull(bookingButton, "Le bouton pour handleBooking doit exister.");

        // Cliquer
        clickOn(bookingButton);
        waitForFxEvents();

        // handleBooking() crée une instance BookingModal() puis .show().
        // Cela ouvre un nouveau stage. On peut vérifier qu'il y a un second stage.
        Stage modalStage = getSecondStage();
        assertNotNull(modalStage, "Le BookingModal devrait s'ouvrir dans un nouveau Stage.");
        assertTrue(modalStage.isShowing(), "Le stage de la BookingModal doit être affiché.");
        
        // Optionnel: vérifier le titre du stage si votre BookingModal le définit
        // assertEquals("Booking", modalStage.getTitle());
    }

    @Test
    @DisplayName("Cliquer sur le bouton handleManageBooking => ouvre la BookingManagementModal")
    void testHandleManageBooking() {
        // Idem que ci-dessus, on suppose un fx:id="manageBookingButton"
        Button manageBookingButton = lookup("#manageBookingButton").queryButton();
        assertNotNull(manageBookingButton);

        // Cliquer
        clickOn(manageBookingButton);
        waitForFxEvents();

        Stage modalStage = getSecondStage();
        assertNotNull(modalStage, "BookingManagementModal devrait s'ouvrir dans un nouveau Stage.");
        assertTrue(modalStage.isShowing());
        
        // Optionnel: vérifier titre, contenu, etc.
    }

    // Méthode utilitaire pour récupérer le second stage
    // (celui qui n'est pas le primaryStage).
    private Stage getSecondStage() {
        return listTargetWindows().stream()
                .filter(window -> window instanceof Stage)
                .map(window -> (Stage) window)
                .filter(s -> s != primaryStage)
                .findFirst()
                .orElse(null);
    }
}
