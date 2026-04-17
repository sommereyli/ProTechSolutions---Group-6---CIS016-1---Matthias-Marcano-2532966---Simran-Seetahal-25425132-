package com.cts.protechsolutionsjavafxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

// Controller for the main welcome screen that directs users to either the admin or customer views
public class MainController {

    // Switches the interface to the admin login screen when the login button is clicked
    @FXML
    public void handleAdminLogin(ActionEvent event) throws IOException {
        // Load the FXML file for the login view
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/LoginView.fxml"));

        // Identify the current window stage from the source of the button click
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Apply the login scene and display the window
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    // Switches the interface to the customer search portal when the search button is clicked
    @FXML
    public void handleCustomerSearch(ActionEvent event) throws IOException {
        // Load the FXML file for the customer search view
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/CustomerSearchView.fxml"));

        // Identify the current window stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene and adjust the window size to fit the search portal layout
        stage.setScene(new Scene(root, 600, 650));
        stage.show();
    }
}