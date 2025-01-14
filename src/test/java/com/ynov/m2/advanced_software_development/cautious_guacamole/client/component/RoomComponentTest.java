package com.ynov.m2.advanced_software_development.cautious_guacamole.client.component;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TestFX permet de lancer une application JavaFX "headless"
 * et de tester l'interface.
 */
public class RoomComponentTest extends ApplicationTest {

    private RoomComponent roomComponentUnderTest;

    @Override
    public void start(Stage stage) throws Exception {
        // Instancie manuellement le composant
        roomComponentUnderTest = new RoomComponent();

        // On ajoute le composant dans une scène JavaFX
        Scene scene = new Scene(roomComponentUnderTest, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void testComponentLoad() {
        // Vérifie que la racine (RoomComponent) n'est pas nulle
        assertNotNull(roomComponentUnderTest);

        // Vérifie qu'on a bien étendu Region
        assertTrue(roomComponentUnderTest instanceof Region);

        // On peut vérifier la présence d'éléments depuis le FXML
        // Par exemple, si "room-component.fxml" contient un label ayant fx:id="labelTitle"
        Label label = (Label) roomComponentUnderTest.lookup("#labelTitle");
        // Si l'élément existe dans le FXML, on s'assure qu'il n'est pas null
        assertNotNull(label, "Le label #labelTitle devrait être chargé depuis le FXML");
        // On peut aussi vérifier son contenu texte
        assertFalse(label.getText().isEmpty(), "Le label ne doit pas être vide");
    }
}
