package com.ynov.m2.advanced_software_development.cautious_guacamole.client.page;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Chargement du fichier FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin-page.fxml"));

            // Création et définition de la scène
            Scene scene = new Scene(fxmlLoader.load(), 1525, 775);
            primaryStage.setTitle("Admin");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            // Gestion de l'erreur si le fichier FXML ne peut pas être chargé
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page d'amin FXML.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}