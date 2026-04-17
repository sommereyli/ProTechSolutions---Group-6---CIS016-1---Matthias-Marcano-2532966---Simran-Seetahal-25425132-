package com.cts.protechsolutionsjavafxapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.stage.Stage;
import java.io.IOException;

// Controller for the screen that allows updating ticket statuses and repair costs
public class UpdateStatusController {

    // Text fields and UI components for searching and editing ticket data
    @FXML private TextField txtSearchId;
    @FXML private VBox vboxUpdateForm;
    @FXML private Label lblDeviceDetails;
    @FXML private Label lblMessage;

    // Inputs for the new status and the parts/labor pricing
    @FXML private ComboBox<String> comboStatus;
    @FXML private TextField txtPartsCost;
    @FXML private TextField txtLaborCost;

    // Variable to track the ticket currently being edited
    private StandardTicket currentTicket;

    // Runs automatically when the screen is loaded
    @FXML
    public void initialize() {
        // Adds the list of available status options to the dropdown menu
        ObservableList<String> statuses = FXCollections.observableArrayList(
                "Under Inspection", "Waiting for Parts", "Repair in Progress", "Ready for Pickup", "Completed"
        );
        comboStatus.setItems(statuses);

        // Disables the update section so it cannot be used until a ticket is found
        vboxUpdateForm.setDisable(true);
    }

    // Searches the database for a specific ticket ID
    @FXML
    public void handleSearch(ActionEvent event) {
        String searchId = txtSearchId.getText().trim();
        lblMessage.setText("");

        // SQL query to pull the specific device and cost info for the entered ID
        String sql = "SELECT device_info, device_model, repair_status, parts_cost, labor_cost FROM tickets WHERE ticket_id = ?";

        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, searchId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // If a match is found, enable the form and display the device details
                vboxUpdateForm.setDisable(false);
                lblDeviceDetails.setText("Device: " + rs.getString("device_info") + " " + rs.getString("device_model"));

                // Fill the input fields with the current values from the database
                comboStatus.setValue(rs.getString("repair_status"));
                txtPartsCost.setText(String.valueOf(rs.getDouble("parts_cost")));
                txtLaborCost.setText(String.valueOf(rs.getDouble("labor_cost")));

                lblMessage.setText("Ticket Found. You can now update the details.");
                lblMessage.setStyle("-fx-text-fill: #2c3e50;");
            } else {
                // Show an error if the ID does not exist in the database
                lblMessage.setText("Error: Ticket ID not found.");
                lblMessage.setStyle("-fx-text-fill: #E74C3C;");
                vboxUpdateForm.setDisable(true);
            }

        } catch (SQLException e) {
            lblMessage.setText("Database error during search.");
            e.printStackTrace();
        }
    }

    // Saves the updated status and costs back to the MySQL database
    @FXML
    public void handleSaveUpdate(ActionEvent event) {
        String searchId = txtSearchId.getText().trim();
        String newStatus = comboStatus.getValue();

        try {
            // Converts the text input for costs into double values for calculation
            double newParts = Double.parseDouble(txtPartsCost.getText());
            double newLabor = Double.parseDouble(txtLaborCost.getText());

            // SQL statement to update the existing record
            String sql = "UPDATE tickets SET repair_status = ?, parts_cost = ?, labor_cost = ? WHERE ticket_id = ?";

            try (Connection conn = DatabaseHandler.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, newStatus);
                pstmt.setDouble(2, newParts);
                pstmt.setDouble(3, newLabor);
                pstmt.setString(4, searchId);

                int rowsAffected = pstmt.executeUpdate();

                // Check if the database was actually updated successfully
                if (rowsAffected > 0) {
                    lblMessage.setText("Success: Ticket " + searchId + " has been updated!");
                    lblMessage.setStyle("-fx-text-fill: #27AE60;");

                    // Clear the search field and lock the form until the next search
                    txtSearchId.clear();
                    vboxUpdateForm.setDisable(true);
                }
            }
        } catch (NumberFormatException e) {
            // Error handling for when the user enters non-numeric text in cost fields
            lblMessage.setText("Error: Costs must be valid numbers (e.g. 50.00)");
            lblMessage.setStyle("-fx-text-fill: #E74C3C;");
        } catch (SQLException e) {
            lblMessage.setText("Database error during update.");
            e.printStackTrace();
        }
    }

    // Navigation method to switch to the Create Ticket screen
    @FXML
    public void handleNavCreate(ActionEvent event) throws IOException {
        switchScene(event, "/com/cts/protechsolutionsjavafxapp/CreateTicketView.fxml", 850, 600);
    }

    // Navigation method to return to the main welcome screen
    @FXML
    public void handleLogout(ActionEvent event) throws IOException {
        switchScene(event, "/com/cts/protechsolutionsjavafxapp/MainView.fxml", 600, 400);
    }

    // Helper method to handle scene transitions and window resizing
    private void switchScene(ActionEvent event, String fxmlPath, int width, int height) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, width, height));
        stage.show();
    }

    // Navigation method to go back to the Admin Dashboard
    @FXML
    public void handleNavDashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/AdminDashboardView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Reset the window size for the dashboard layout
        stage.setScene(new Scene(root, 750, 500));
        stage.show();
    }
}
