package com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

/**
 * TestFX pour BookingModal :
 * - Vérifie l'ouverture et la fermeture 
 * - Vérifie (optionnellement) le titre, la taille, le centrage, etc.
 */
public class BookingModalFxTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        // On ne remplace pas la scène principale.
        // La modal sera créée dans le test (bookingModal.show()).
        // 'stage' reste le stage principal géré par TestFX.
        stage.show();
    }

    @Test
    void testShowAndCloseModal() {
        // 1) Création de la modal
        BookingModal bookingModal = new BookingModal();

        // 2) Affichage (showAndWait est bloquant, donc on doit le lancer dans un runLater())
        javafx.application.Platform.runLater(bookingModal::show);
        waitForFxEvents(); // Synchronise l'exécution JavaFX

        // 3) Vérification : la modal doit être visible dans la liste des Stages
        Stage modalStage = getModalStage();
        assertNotNull(modalStage, "La BookingModal doit créer un Stage non-null");
        assertTrue(modalStage.isShowing(), "La modal doit être affichée.");

        // Vérifier le titre si vous voulez
        assertEquals("réservation de salle", modalStage.getTitle());

        // 4) Fermeture
        javafx.application.Platform.runLater(bookingModal::close);
        waitForFxEvents();

        // Après fermeture, le stage ne doit plus être affiché
        assertFalse(modalStage.isShowing(), "La modal doit être fermée.");
    }

    /**
     * Récupère le Stage créé par BookingModal (en se basant sur son titre).
     */
    private Stage getModalStage() {
        return listTargetWindows().stream()
                .filter(window -> window instanceof Stage)
                .map(window -> (Stage) window)
                .filter(s -> "réservation de salle".equals(s.getTitle()))
                .findFirst()
                .orElse(null);
    }
}
