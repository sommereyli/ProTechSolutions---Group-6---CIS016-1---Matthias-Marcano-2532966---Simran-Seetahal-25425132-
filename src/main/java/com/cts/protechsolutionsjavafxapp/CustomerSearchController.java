package com.cts.protechsolutionsjavafxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;

// Controller for the customer search screen
// Lets customers type in their ticket ID to check on their repair status
public class CustomerSearchController {

    // Text box and labels connected to the UI
    @FXML private TextField txtSearchId;
    @FXML private Label lblError;

    // The container box that holds the search results
    @FXML private VBox vboxResults;

    // Labels inside the results box to show the actual ticket details
    @FXML private Label lblDevice;
    @FXML private Label lblIssue;
    @FXML private Label lblStatus;
    @FXML private Label lblCost;

    // Runs automatically when the screen first loads up
    @FXML
    public void initialize() {
        // Hide the grey results box at first since no search has happened yet
        vboxResults.setVisible(false);
    }

    // Triggers when the search button is clicked
    @FXML
    public void handleSearch(ActionEvent event) {
        // Get the text the user typed and remove any accidental spaces
        String searchId = txtSearchId.getText().trim();

        // Basic check to make sure the search box isn't empty before checking the database
        if (searchId.isEmpty()) {
            lblError.setText("Please enter a Ticket ID.");
            vboxResults.setVisible(false);
            return;
        }

        // Database query to find the ticket matching the exact ID typed in
        String sql = "SELECT * FROM tickets WHERE ticket_id = ?";

        // Try connecting to the database and setting up the query safely
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Put the search ID into the question mark placeholder
            pstmt.setString(1, searchId);

            // Run the search and store the results
            ResultSet rs = pstmt.executeQuery();

            // If a matching ticket is found in the database
            if (rs.next()) {
                // Clear any previous error messages and show the results card
                lblError.setText("");
                vboxResults.setVisible(true);

                // Pull out all the specific details from the database columns
                String brand = rs.getString("device_info");
                String model = rs.getString("device_model");
                String fault = rs.getString("fault_description");
                String status = rs.getString("repair_status");
                double parts = rs.getDouble("parts_cost");
                double labor = rs.getDouble("labor_cost");

                // Update the text on the screen with the database info
                lblDevice.setText("Device: " + brand + " " + model);
                lblIssue.setText("Reported Issue: " + fault);
                lblStatus.setText(status);

                // Calculate the total cost by adding parts and labor together
                double total = parts + labor;

                // Format the total to look like real money with 2 decimal places
                lblCost.setText("Estimated Cost: $" + String.format("%.2f", total));

            } else {
                // If no ticket matches the ID entered
                lblError.setText("Error: Ticket ID '" + searchId + "' not found.");
                vboxResults.setVisible(false);
            }

        } catch (SQLException e) {
            // Catch database errors like if the server is offline
            lblError.setText("Database Connection Error.");
            e.printStackTrace();
        }
    }

    // Action for the back button
    // Returns to the main welcome screen
    @FXML
    public void handleBackClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/MainView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
}
