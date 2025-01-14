package com.ynov.m2.advanced_software_development.cautious_guacamole.client.page;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpPage extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SignUpPage.class.getResource("sign-up-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1525, 775);
        primaryStage.setTitle("Inscription");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}