package com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

/**
 * Test d'interface pour ManageRoomModal utilisant TestFX.
 * Vérifie le chargement FXML, l'ouverture/fermeture et le titre.
 */
public class ManageRoomModalFxTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        // On laisse le stage principal vide. 
        // La modal sera créée via new ManageRoomModal() dans le test.
        stage.show();
    }

    @Test
    void testShowAndCloseModal() {
        // 1) Créer la modal
        ManageRoomModal modal = new ManageRoomModal();

        // 2) Afficher (showAndWait est bloquant -> on passe par runLater())
        javafx.application.Platform.runLater(modal::show);
        waitForFxEvents();

        // 3) Vérifier l'existence du Stage
        Stage modalStage = getModalStage();
        assertNotNull(modalStage, "Le stage pour ManageRoomModal doit être créé.");
        assertTrue(modalStage.isShowing(), "La modal doit être affichée.");
        assertEquals("gestion de la salle", modalStage.getTitle());

        // 4) Fermer la modal
        javafx.application.Platform.runLater(modal::close);
        waitForFxEvents();

        // 5) Vérifier qu'elle est fermée
        assertFalse(modalStage.isShowing(), "Le stage doit être fermé après close().");
    }

    /**
     * Recherche dans les fenêtres connues de TestFX le stage dont le titre est "gestion de la salle".
     */
    private Stage getModalStage() {
        return listTargetWindows().stream()
            .filter(window -> window instanceof Stage)
            .map(window -> (Stage) window)
            .filter(s -> "gestion de la salle".equals(s.getTitle()))
            .findFirst()
            .orElse(null);
    }
}
