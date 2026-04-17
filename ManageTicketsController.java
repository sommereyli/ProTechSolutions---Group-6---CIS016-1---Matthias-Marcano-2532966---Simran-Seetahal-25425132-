package com.cts.protechsolutionsjavafxapp;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Controller for the screen that displays all repair tickets in a table
// Allows for viewing, searching, and deleting records from the database
public class ManageTicketsController {

    // UI components for the table and search functionality
    @FXML private TableView<StandardTicket> tableTickets;
    @FXML private TableColumn<StandardTicket, String> colId;
    @FXML private TableColumn<StandardTicket, String> colCustomer;
    @FXML private TableColumn<StandardTicket, String> colDevice;
    @FXML private TableColumn<StandardTicket, String> colStatus;
    @FXML private TableColumn<StandardTicket, String> colCost;

    @FXML private TextField txtSearch;
    @FXML private Label lblMessage;

    // List used to store and display ticket data in the TableView
    private ObservableList<StandardTicket> ticketList = FXCollections.observableArrayList();

    // Sets up the table columns and loads initial data when the screen opens
    @FXML
    public void initialize() {
        // Link each table column to the corresponding data in the StandardTicket object
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTicketID()));
        colCustomer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getName()));
        colDevice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDevice().getBrand() + " " + cellData.getValue().getDevice().getModel()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        // Format the total cost to two decimal places for the table display
        colCost.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f", cellData.getValue().calculateTotal())));

        // Fetch all records from the database to fill the table
        handleLoadAll(null);
    }

    // Connects to the database and retrieves every ticket record
    @FXML
    public void handleLoadAll(ActionEvent event) {
        ticketList.clear();
        String sql = "SELECT * FROM tickets";

        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Loop through the database results and reconstruct the objects
            while (rs.next()) {
                Customer c = new Customer(rs.getString("customer_name"), rs.getString("phone_number"));
                Device d = new Device(rs.getString("device_info"), rs.getString("device_model"), rs.getString("fault_description"));
                StandardTicket t = new StandardTicket(rs.getString("ticket_id"), c, d);

                // Set the additional details retrieved from the database
                t.setStatus(rs.getString("repair_status"));
                t.setPartsCost(rs.getDouble("parts_cost"));
                t.setLaborCost(rs.getDouble("labor_cost"));

                ticketList.add(t);
            }
            // Update the table with the full list of tickets
            tableTickets.setItems(ticketList);
            lblMessage.setText("All tickets loaded.");

        } catch (SQLException e) {
            lblMessage.setText("Database error: Could not load tickets.");
            e.printStackTrace();
        }
    }

    // Filters the table based on the text entered in the search box
    @FXML
    public void handleSearch(ActionEvent event) {
        String query = txtSearch.getText().trim();

        // If the search box is empty, just reload everything
        if (query.isEmpty()) {
            handleLoadAll(null);
            return;
        }

        // Create a temporary list to hold matches found during the search
        ObservableList<StandardTicket> filteredList = FXCollections.observableArrayList();
        for (StandardTicket t : ticketList) {
            // Check if the search text matches either the Ticket ID or the Customer Name
            if (t.getTicketID().toLowerCase().contains(query.toLowerCase()) ||
                    t.getCustomer().getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(t);
            }
        }
        // Show only the filtered results in the table
        tableTickets.setItems(filteredList);
        lblMessage.setText("Showing search results.");
    }

    // Deletes the selected ticket from both the database and the table
    @FXML
    public void handleDelete(ActionEvent event) {
        // Identify which row the user has selected
        StandardTicket selectedTicket = tableTickets.getSelectionModel().getSelectedItem();

        // Ensure a selection was made before attempting to delete
        if (selectedTicket == null) {
            lblMessage.setText("Please select a ticket from the table to delete.");
            return;
        }

        String sql = "DELETE FROM tickets WHERE ticket_id = ?";

        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, selectedTicket.getTicketID());
            pstmt.executeUpdate();

            // Remove the item from the list so the table updates immediately
            ticketList.remove(selectedTicket);
            lblMessage.setText("Ticket " + selectedTicket.getTicketID() + " deleted permanently.");

        } catch (SQLException e) {
            lblMessage.setText("Database error during deletion.");
            e.printStackTrace();
        }
    }

    // Returns the user to the main Admin Dashboard
    @FXML
    public void handleNavDashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/AdminDashboardView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 750, 500));
        stage.show();
    }

    // Logs out and returns to the main welcome screen
    @FXML
    public void handleLogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/MainView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
}
