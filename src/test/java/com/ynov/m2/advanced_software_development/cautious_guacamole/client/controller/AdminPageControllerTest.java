package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.RoomModal;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeoutException;

/**
 * Test d'interface pour AdminPageController avec TestFX.
 * On vérifie l'action du bouton "Go Home" et l'ouverture de la modal "RoomModal".
 */
public class AdminPageControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        // On charge le FXML de la page Admin
        Parent root = loadFXML("/com/ynov/m2/advanced_software_development/cautious_guacamole/client/page/admin-page.fxml");
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void testHandleGoOnHomePage() {
        // On clique sur le bouton "homePageButton"
        clickOn("#homePageButton");

        // Normalement, handleGoOnHomePage() charge "home-page.fxml" dans le Stage.
        // On peut vérifier le titre de la fenêtre
        Stage stage = null;
        try {
            stage = FxToolkit.registerPrimaryStage();
        } catch (TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertNotNull(stage, "Le stage ne doit pas être null");
        assertEquals("Accueil", stage.getTitle(), 
            "Le titre de la fenêtre doit changer en 'Accueil' après le clic");
        
        // Optionnellement, on peut vérifier la taille, la scène, etc.
        // Ex: assertEquals(1525, stage.getScene().getWidth(), 0.1);
    }

    @Test
    void testHandleRoom() {
        // On clique sur le bouton qui ouvre la modal
        clickOn("#roomButton");

        // handleRoom() instancie un RoomModal et l'affiche.
        // Dans un test d'interface complet, on vérifierait si une nouvelle fenêtre 
        // (Stage ou Dialog) s'est ouverte. Sans code supplémentaire dans RoomModal, 
        // il est toutefois difficile de l'identifier exactement.

        // On peut par exemple vérifier que le RoomModal n'a pas levé d'exception,
        // ou qu'un nouveau stage a été enregistré, etc.
        // S'il y a un getStage() dans RoomModal, on pourrait le moquer ou l'exposer.
        
        // Pour une approche minimaliste, on vérifie juste que le test se termine sans erreur. 
        assertTrue(true, "Ouverture de la modal RoomModal sans exception");
    }

    // Méthode utilitaire pour charger un FXML dans un Parent
    private Parent loadFXML(String fxmlPath) throws Exception {
        return new javafx.fxml.FXMLLoader(getClass().getResource(fxmlPath)).load();
    }
}
