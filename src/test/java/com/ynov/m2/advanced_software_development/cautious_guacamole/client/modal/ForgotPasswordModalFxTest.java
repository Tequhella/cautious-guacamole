package com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

/**
 * Test d’interface pour ForgotPasswordModal en utilisant TestFX.
 * Valide le chargement FXML, l’ouverture et la fermeture.
 */
public class ForgotPasswordModalFxTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        // On ne change pas la scène principale de TestFX, 
        // la modal sera créée plus tard dans le test.
        stage.show();
    }

    @Test
    void testShowAndCloseModal() {
        // 1) Création de la modal
        ForgotPasswordModal modal = new ForgotPasswordModal();

        // 2) Affichage
        //   Comme showAndWait() est bloquant, on le lance dans un Platform.runLater
        javafx.application.Platform.runLater(modal::show);
        waitForFxEvents();  // on attend la fin des events JavaFX

        // 3) Vérifier que la modal est bien ouverte
        Stage modalStage = getModalStage();
        assertNotNull(modalStage, "La ForgotPasswordModal devrait créer un Stage non null");
        assertTrue(modalStage.isShowing(), "La modal doit être affichée.");
        assertEquals("Mot de passe oublié", modalStage.getTitle(),
            "Le titre doit correspondre à celui défini dans la classe ForgotPasswordModal.");

        // 4) Fermer la modal
        javafx.application.Platform.runLater(modal::close);
        waitForFxEvents();

        // 5) Vérifier qu’elle est fermée
        assertFalse(modalStage.isShowing(), "La modal doit être fermée après close().");
    }

    /**
     * Recherche le Stage correspondant à la ForgotPasswordModal 
     * en se basant sur le titre "Mot de passe oublié".
     */
    private Stage getModalStage() {
        return listTargetWindows().stream()
            .filter(window -> window instanceof Stage)
            .map(window -> (Stage) window)
            .filter(s -> "Mot de passe oublié".equals(s.getTitle()))
            .findFirst()
            .orElse(null);
    }
}
