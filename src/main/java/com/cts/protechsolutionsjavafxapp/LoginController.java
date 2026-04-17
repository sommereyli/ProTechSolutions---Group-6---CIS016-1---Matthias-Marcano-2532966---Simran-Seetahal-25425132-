package com.cts.protechsolutionsjavafxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

// Controller for the admin login screen
// Verifies credentials and manages navigation to the dashboard
public class LoginController {

    // Text fields for inputting admin credentials
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    // Label to show error messages if login fails
    @FXML
    private Label lblError;

    // Handles the login logic when the user clicks the login button
    @FXML
    public void handleLoginClick(ActionEvent event) {
        // Retrieve the text entered by the user
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Check if the credentials match the set admin login
        if (username.equals("admin") && password.equals("123")) {
            try {
                // Load the dashboard screen upon successful login
                Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/AdminDashboardView.fxml"));

                // Identify the current window to switch the scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Update the scene and set the window size to fit the dashboard
                stage.setScene(new Scene(root, 750, 500));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Display an error message if the username or password is incorrect
            lblError.setText("Error: Invalid username or password!");
            lblError.setStyle("-fx-text-fill: red;");
        }
    }

    // Returns the user to the initial welcome screen
    @FXML
    public void handleBackClick(ActionEvent event) throws IOException {
        // Load the main view file
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/MainView.fxml"));

        // Get the current stage from the button click
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Switch back to the original welcome screen dimensions
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
}
