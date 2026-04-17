package com.cts.protechsolutionsjavafxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

// Controller for the admin dashboard menu
// Handles all the button clicks to navigate between different admin screens
public class AdminDashboardController {

    // Action for the Create Ticket button
    // Swaps the current screen out for the Create Ticket form
    @FXML
    public void handleCreateTicket(ActionEvent event) throws IOException {
        // load the fxml file for creating tickets
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/CreateTicketView.fxml"));

        // get the current window from the button click event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // set the new scene and window size
        stage.setScene(new Scene(root, 850, 600));
        stage.show();
    }

    // Action for the Manage Tickets button
    @FXML
    public void handleManageTickets(ActionEvent event) throws IOException {
        // load the manage tickets screen
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/ManageTicketsView.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // setting the window to 900x600 here
        // made it slightly wider than the others so the data table fits properly without squishing
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }

    // Action for the Update Status button
    @FXML
    public void handleUpdateStatus(ActionEvent event) throws IOException {
        // load the update status screen
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/UpdateStatusView.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 850, 600));
        stage.show();
    }

    // Action for the Logout button
    // Takes the admin back to the main welcome screen
    @FXML
    public void handleLogout(ActionEvent event) throws IOException {
        // load the main view again
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/MainView.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // resize back to the original smaller welcome screen dimensions
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
}
