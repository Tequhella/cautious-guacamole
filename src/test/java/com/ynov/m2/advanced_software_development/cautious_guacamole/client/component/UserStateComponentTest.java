package com.ynov.m2.advanced_software_development.cautious_guacamole.client.component;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test d'intégration UI pour le composant UserStateComponent.
 * Nécessite l'environnement JavaFX, géré par TestFX.
 */
public class UserStateComponentTest extends ApplicationTest {

    private UserStateComponent userStateComponentUnderTest;

    @Override
    public void start(Stage stage) {
        // Instancie le composant
        userStateComponentUnderTest = new UserStateComponent();

        // Crée une scène et y ajoute le composant
        Scene scene = new Scene(userStateComponentUnderTest, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void testComponentLoadsSuccessfully() {
        // Vérifie que le composant n'est pas null
        assertNotNull(userStateComponentUnderTest, "Le composant doit être instancié");

        // Vérifie que le composant hérite bien de Region
        assertTrue(userStateComponentUnderTest instanceof Region);

        // Vérifie la présence d'un nœud du FXML (si vous avez un Label avec fx:id="labelUserState")
        Label label = (Label) userStateComponentUnderTest.lookup("#labelUserState");
        assertNotNull(label, "Le label #labelUserState devrait être présent dans le FXML");
    }
}
