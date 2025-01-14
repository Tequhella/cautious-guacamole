package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.RoomModal;
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
class RoomModalControllerTest {

    @Mock
    private RoomModal mockModal; // Mock de la modal

    private RoomModalController controller;

    // Contrôles JavaFX que l’on va créer à la main
    private TextField seatingCapacityField;
    private CheckComboBox<String> equipmentDropdown;

    @BeforeEach
    void setUp() throws Exception {
        // Instanciation du contrôleur
        controller = new RoomModalController();

        // On injecte le mock de la modal
        controller.setModal(mockModal);

        // Instanciation "manuelle" des contrôles JavaFX
        seatingCapacityField = new TextField();
        equipmentDropdown = new CheckComboBox<>();

        // Injection via réflexion (car le FXMLLoader ne tourne pas ici)
        setPrivateField(controller, "seatingCapacityField", seatingCapacityField);
        setPrivateField(controller, "equipmentDropdown", equipmentDropdown);

        // Appel de la méthode initialize() pour configurer le dropdown et le listener
        controller.initialize();
    }

    @Test
    void testHandleCancel() {
        // handleCancel() doit fermer la modal en appelant mockModal.close()
        controller.handleCancel();

        // Vérification
        verify(mockModal, times(1)).close();
    }

    @Test
    void testInitialize_EquipmentDropdown() {
        // Après l’appel à initialize(), on s’attend à 3 équipements
        assertEquals(3, equipmentDropdown.getItems().size(), 
                "Le CheckComboBox doit contenir 3 équipements");
        assertTrue(equipmentDropdown.getItems().contains("equipement 1"));
        assertTrue(equipmentDropdown.getItems().contains("equipement 2"));
        assertTrue(equipmentDropdown.getItems().contains("equipement 3"));
    }

    @Test
    void testSeatingCapacityField_OnlyDigits() {
        // Simule la saisie dans seatingCapacityField
        // Le listener doit bloquer les caractères non-chiffres

        seatingCapacityField.setText("123"); 
        assertEquals("123", seatingCapacityField.getText());

        // On insère des lettres
        seatingCapacityField.setText("abc123");
        assertEquals("123", seatingCapacityField.getText(),
                "Le champ ne doit contenir que des chiffres");

        // Des caractères spéciaux
        seatingCapacityField.setText("45@!6");
        assertEquals("456", seatingCapacityField.getText());

        // Numérique + espace
        seatingCapacityField.setText("12 34");
        // L’espace n’est pas un chiffre => retiré
        assertEquals("1234", seatingCapacityField.getText());
    }

    // Méthode utilitaire pour injecter un champ privé (via réflexion)
    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
