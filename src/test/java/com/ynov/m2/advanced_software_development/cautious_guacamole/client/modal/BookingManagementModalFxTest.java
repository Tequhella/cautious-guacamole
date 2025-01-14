package com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal;

import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

/**
 * Test d'intégration JavaFX pour BookingManagementModal.
 * Nécessite TestFX pour lancer un contexte JavaFX.
 */
public class BookingManagementModalFxTest extends ApplicationTest {

    private BookingManagementModal modalUnderTest;

    @Override
    public void start(Stage stage) throws Exception {
        // On ne "remplace" pas le Stage principal.
        // On crée et montre la modal dans le test lui-même.
    }

    @Test
    @DisplayName("Test de l'ouverture et de la fermeture de la modal")
    void testShowAndCloseModal() throws Exception {
        // 1) Création de la modal
        modalUnderTest = new BookingManagementModal();

        // 2) Appel de .show()
        //   Cela ouvre la fenêtre en mode modal (showAndWait()) => bloquant
        //   Pour TestFX, on peut l'appeler dans un thread à part, ou l'appeler en Platform.runLater(...) 
        //   afin de ne pas bloquer totalement le test.
        //
        //   Ex.:
        javafx.application.Platform.runLater(() -> {
            modalUnderTest.show(); 
        });
        waitForFxEvents();

        // Vérifier qu'un nouveau Stage est créé
        Stage modalStage = getModalStage();
        assertNotNull(modalStage, "Le stage de la BookingManagementModal doit exister.");
        assertTrue(modalStage.isShowing(), "La modal doit être affichée.");

        // 3) Fermer la modal (Platform.runLater pour ne pas bloquer)
        javafx.application.Platform.runLater(() -> {
            modalUnderTest.close();
        });
        waitForFxEvents();

        // Vérifier que le stage s'est bien fermé
        assertFalse(modalStage.isShowing(), "Le stage de la modal doit être fermé.");
    }

    /**
     * Méthode utilitaire pour récupérer le stage de la BookingManagementModal.
     * On parcourt la liste des fenêtres connues de TestFX.
     */
    private Stage getModalStage() {
        return listTargetWindows().stream()
                .filter(window -> window instanceof Stage)
                .map(window -> (Stage) window)
                // Filtrer : c'est celui qui a le titre "gestion de la réservation" (défini dans BookingManagementModal)
                .filter(s -> "gestion de la réservation".equals(s.getTitle()))
                .findFirst()
                .orElse(null);
    }
}
