package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.BookingModal;
import javafx.collections.ObservableList;
import org.controlsfx.control.CheckComboBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingModalControllerTest {

    @Mock
    private BookingModal mockModal;

    private BookingModalController controller;

    // Un CheckComboBox "réel" (org.controlsfx.control) pour tester l'ajout d'items
    private CheckComboBox<String> checkComboBox;

    @BeforeEach
    void setUp() {
        // Instancie le contrôleur
        controller = new BookingModalController();
        // Injecte la modal mockée
        controller.setModal(mockModal);

        // Crée un CheckComboBox "manuel" (sans FXML)
        checkComboBox = new CheckComboBox<>();
        
        // On simule l'injection via @FXML en affectant le champ userDropdown
        // Normalement, c'est le FXMLLoader qui ferait ceci.
        // Mais ici, on le fait à la main pour permettre de tester "initialize()".
        try {
            var userDropdownField = BookingModalController.class.getDeclaredField("userDropdown");
            userDropdownField.setAccessible(true);
            userDropdownField.set(controller, checkComboBox);
        } catch (Exception e) {
            throw new RuntimeException("Impossible d'injecter le champ userDropdown", e);
        }
    }

    @Test
    void testHandleCancel() {
        // Méthode handleCancel() doit appeler modal.close()
        controller.handleCancel();
        verify(mockModal, times(1)).close();
    }

    @Test
    void testInitialize() {
        // Appel de la méthode initialize() du contrôleur
        controller.initialize();

        // Vérifie que user1, user2, user3 ont été ajoutés
        ObservableList<String> items = checkComboBox.getItems();
        assertEquals(3, items.size(), "On doit avoir 3 items dans le CheckComboBox");
        assertTrue(items.contains("user1"));
        assertTrue(items.contains("user2"));
        assertTrue(items.contains("user3"));

        // Vérifie la présence d'un listener sur getCheckedItems()
        // (facultatif : on peut juste vérifier qu'il n'y a pas d'erreur)
        // Dans un vrai test JavaFX, on pourrait cocher des éléments et vérifier la console ou un callback.
        // Ici, on se limite à vérifier que la méthode s'exécute sans exception.
    }
}
