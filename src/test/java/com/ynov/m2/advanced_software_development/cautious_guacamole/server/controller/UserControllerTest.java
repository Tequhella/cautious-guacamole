package com.ynov.m2.advanced_software_development.cautious_guacamole.server.controller;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.model.user.User;
import com.ynov.m2.advanced_software_development.cautious_guacamole.server.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId(1L);
        user1.setUsername("Alice");
        user1.setPassword("password1");

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("Bob");
        user2.setPassword("password2");
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> mockUsers = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(mockUsers);

        // Act
        List<User> result = userController.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getUsername());
        assertEquals("Bob", result.get(1).getUsername());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById_Found() {
        // Arrange
        when(userService.getUserById(1L)).thenReturn(user1);

        // Act
        ResponseEntity<User> response = userController.getUserById(1L);

        // Assert
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(user1, response.getBody());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        // Arrange
        when(userService.getUserById(1L)).thenReturn(null);

        // Act
        ResponseEntity<User> response = userController.getUserById(1L);

        // Assert
        assertNotNull(response);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertFalse(response.hasBody());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testCreateUser_Success() throws Exception {
        // Arrange
        when(userService.registerUser(any(User.class))).thenReturn(user1);

        // Act
        ResponseEntity<?> response = userController.createUser(user1);

        // Assert
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(user1, response.getBody());
        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    void testCreateUser_Exception() throws Exception {
        // Arrange
        when(userService.registerUser(any(User.class))).thenThrow(new RuntimeException("Erreur création"));

        // Act
        ResponseEntity<?> response = userController.createUser(user2);

        // Assert
        assertNotNull(response);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals("Erreur création", response.getBody());
        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    void testUpdateUser_Success() throws Exception {
        // Arrange
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(user2);

        // Act
        ResponseEntity<?> response = userController.updateUser(1L, user2);

        // Assert
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(user2, response.getBody());
        verify(userService, times(1)).updateUser(eq(1L), any(User.class));
    }

    @Test
    void testUpdateUser_Exception() throws Exception {
        // Arrange
        when(userService.updateUser(eq(1L), any(User.class))).thenThrow(new RuntimeException("Erreur mise à jour"));

        // Act
        ResponseEntity<?> response = userController.updateUser(1L, user2);

        // Assert
        assertNotNull(response);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals("Erreur mise à jour", response.getBody());
        verify(userService, times(1)).updateUser(eq(1L), any(User.class));
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        // Arrange
        doNothing().when(userService).deleteUser(1L);

        // Act
        ResponseEntity<?> response = userController.deleteUser(1L);

        // Assert
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testDeleteUser_Exception() throws Exception {
        // Arrange
        doThrow(new RuntimeException("User not found")).when(userService).deleteUser(1L);

        // Act
        ResponseEntity<?> response = userController.deleteUser(1L);

        // Assert
        assertNotNull(response);
        // Ici on s'attend à un status 404
        assertTrue(response.getStatusCode().is4xxClientError());
        verify(userService, times(1)).deleteUser(1L);
    }
}