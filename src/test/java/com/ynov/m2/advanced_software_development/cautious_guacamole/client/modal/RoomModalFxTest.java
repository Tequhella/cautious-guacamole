package com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

/**
 * Test d'interface JavaFX pour RoomModal.
 * On vérifie l'ouverture (show) et la fermeture (close).
 */
public class RoomModalFxTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        // On ne modifie pas la scène principale
        // La modal sera créée plus tard dans le test.
        stage.show();
    }

    @Test
    void testShowAndClose() {
        // 1) Création de la RoomModal
        RoomModal roomModal = new RoomModal();

        // 2) Affichage
        //   showAndWait() est bloquant, donc on le lance dans un Platform.runLater(...)
        //   puis on attend la fin des événements JavaFX.
        javafx.application.Platform.runLater(roomModal::show);
        waitForFxEvents();

        // 3) Vérification : la modal doit être visible dans la liste des Stages
        Stage modalStage = getModalStage();
        assertNotNull(modalStage, "La RoomModal doit créer un Stage non null");
        assertTrue(modalStage.isShowing(), "La fenêtre modale doit être affichée.");
        assertEquals("création de salle", modalStage.getTitle());

        // 4) Fermeture
        javafx.application.Platform.runLater(roomModal::close);
        waitForFxEvents();

        // 5) Vérification : la fenêtre doit être fermée
        assertFalse(modalStage.isShowing(), "La fenêtre modale doit être fermée.");
    }

    // Récupère le Stage correspondant à la RoomModal (titre "création de salle")
    private Stage getModalStage() {
        return listTargetWindows().stream()
            .filter(window -> window instanceof Stage)
            .map(window -> (Stage) window)
            .filter(s -> "création de salle".equals(s.getTitle()))
            .findFirst()
            .orElse(null);
    }
}
