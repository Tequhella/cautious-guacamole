package com.ynov.m2.advanced_software_development.cautious_guacamole.client.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.client.modal.BookingManagementModal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingManagementModalControllerTest {

    @Mock
    private BookingManagementModal mockModal;

    private BookingManagementModalController controller;

    @BeforeEach
    void setUp() {
        controller = new BookingManagementModalController();
        // On injecte la référence de la modal mockée
        controller.setModal(mockModal);
    }

    @Test
    void testHandleCancel() {
        // Act
        controller.handleCancel();

        // Assert
        // Vérifie que mockModal.close() a bien été appelé
        verify(mockModal, times(1)).close();
    }
}
