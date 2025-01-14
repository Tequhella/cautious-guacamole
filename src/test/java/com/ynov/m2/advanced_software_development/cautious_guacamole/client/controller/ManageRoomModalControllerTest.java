package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.ManageRoomModal;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManageRoomModalControllerTest {

    @Mock
    private ManageRoomModal mockModal;   // Modal mockée pour tester handleCancel()

    private ManageRoomModalController controller;

    // Contrôles JavaFX que nous allons instancier "à la main"
    private TextField seatingCapacityField;
    private CheckComboBox<String> equipmentDropdown;

    @BeforeEach
    void setUp() throws Exception {
        // Instanciation du contrôleur
        controller = new ManageRoomModalController();
        // On injecte la modal mockée
        controller.setModal(mockModal);

        // Instanciation "manuelle" des contrôles
        seatingCapacityField = new TextField();
        equipmentDropdown = new CheckComboBox<>();

        // Injection via réflexion (car le code FXMLLoader ne tourne pas)
        setPrivateField(controller, "seatingCapacityField", seatingCapacityField);
        setPrivateField(controller, "equipmentDropdown", equipmentDropdown);

        // On appelle la méthode initialize() pour remplir la liste, etc.
        controller.initialize();
    }

    @Test
    void testHandleCancel() {
        // handleCancel() doit appeler modal.close()
        controller.handleCancel();
        verify(mockModal, times(1)).close();
    }

    @Test
    void testInitialize_EquipmentDropdown() {
        // Après l'appel à initialize(), on s'attend à avoir 3 équipements
        assertEquals(3, equipmentDropdown.getItems().size(),
                     "Le CheckComboBox doit avoir 3 équipements");
        assertTrue(equipmentDropdown.getItems().contains("equipement 1"));
        assertTrue(equipmentDropdown.getItems().contains("equipement 2"));
        assertTrue(equipmentDropdown.getItems().contains("equipement 3"));
    }

    @Test
    void testSeatingCapacityField_ListenerAcceptsOnlyDigits() {
        // On va simuler la saisie d'un texte "abc123".
        // Comme le listener remplace les caractères non-chiffres, 
        // on s'attend à "123" à la fin.

        seatingCapacityField.setText("");  // champ vide au départ

        // On simule un changement de texte
        seatingCapacityField.setText("abc123");

        // Vérifions qu'après l'affectation, le listener a fait le tri
        // (dans la vraie vie, le listener se déclenche sur l'événement "text changed").
        assertEquals("123", seatingCapacityField.getText(),
                "Le champ doit ne contenir que des chiffres");
        
        // On peut simuler d'autres cas
        seatingCapacityField.setText("12!45#");
        assertEquals("1245", seatingCapacityField.getText());
    }

    // -----------------------------------------------------------------------
    // Méthode utilitaire pour injecter un champ privé via réflexion
    // -----------------------------------------------------------------------
    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
