package com.cts.protechsolutionsjavafxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Main class that starts up the whole JavaFX app
public class App extends Application {

    // This runs as soon as the app launches
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Just testing if the database actually connects before loading everything else
        // helps catch errors early
        try (java.sql.Connection conn = DatabaseHandler.getConnection()) {
            if (conn != null) {
                System.out.println("SUCCESS: Connected to MySQL!");
            }
        } catch (Exception e) {
            System.out.println("FAILED: Could not connect.");
            e.printStackTrace();
        }

        // Loading the main screen FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/MainView.fxml"));

        // Creating the scene and setting the default window size to 600x400
        Scene scene = new Scene(root, 600, 400);

        // Setting up the window title and attaching the scene
        primaryStage.setTitle("ProTech Solutions - Device Repair Tracking System");
        primaryStage.setScene(scene);

        // Disabling resize so the UI doesn't get messed up if the user stretches the window
        primaryStage.setResizable(false);

        // Actually show the window on screen
        primaryStage.show();
    }

    // Standard main method needed to kick off the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}