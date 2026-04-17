package com.cts.protechsolutionsjavafxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.io.IOException;
import java.util.ArrayList;

// Controller for the screen where new repair tickets are created
// Grabs text from the form and saves it to the database
public class CreateTicketController {

    // UI elements linked to the fxml file
    @FXML private TextField txtName;
    @FXML private TextField txtPhone;
    @FXML private TextField txtBrand;
    @FXML private TextField txtModel;
    @FXML private TextArea txtIssue;
    @FXML private Label lblStatus;

    // Global list to store tickets locally
    // Kept this here just in case the customer search screen needs to read from it later
    public static ArrayList<RepairTicket> globalTicketList = new ArrayList<>();

    // Runs when the generate ticket button is clicked
    @FXML
    public void handleGenerateTicket(ActionEvent event) {

        // Check if the user left any important text boxes empty
        if (txtName.getText().isEmpty() || txtPhone.getText().isEmpty() || txtBrand.getText().isEmpty()) {
            lblStatus.setText("Error: Missing Information!");
            lblStatus.setStyle("-fx-text-fill: #E74C3C;"); // red error color
            return;
        }

        // Make a random 4-digit number and stick "PRO-" in front of it to make a unique ticket ID
        int randomNumber = (int) (Math.random() * 9000) + 1000;
        String uniqueID = "PRO-" + randomNumber;

        // Create the customer, device, and ticket objects using the data typed into the form
        Customer customer = new Customer(txtName.getText(), txtPhone.getText());
        Device device = new Device(txtBrand.getText(), txtModel.getText(), txtIssue.getText());
        StandardTicket ticket = new StandardTicket(uniqueID, customer, device);

        // Save everything to the MySQL database
        // The sql query with question marks as placeholders for the actual data
        String sql = "INSERT INTO tickets (ticket_id, customer_name, phone_number, device_info, device_model, fault_description, repair_status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Try-with-resources automatically closes the database connection when done
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Mapping the object data to the SQL query question mark placeholders
            pstmt.setString(1, ticket.getTicketID());
            pstmt.setString(2, ticket.getCustomer().getName());
            pstmt.setString(3, ticket.getCustomer().getPhoneNumber());
            pstmt.setString(4, ticket.getDevice().getBrand());
            pstmt.setString(5, ticket.getDevice().getModel());
            pstmt.setString(6, ticket.getDevice().getFaultDescription());
            pstmt.setString(7, ticket.getStatus());

            // Run the insert command to save to database
            pstmt.executeUpdate();

            // Update the status label at the bottom to show it worked (dark blue color)
            lblStatus.setText("Successfully Saved! ID: " + uniqueID);
            lblStatus.setStyle("-fx-text-fill: #2c3e50;");

            // Clear all the text boxes so the form is ready for the next ticket
            txtName.clear();
            txtPhone.clear();
            txtBrand.clear();
            txtModel.clear();
            txtIssue.clear();

        } catch (SQLException e) {
            // Show a red error message if the database connection fails
            lblStatus.setText("Database Error: Could not save ticket.");
            lblStatus.setStyle("-fx-text-fill: #E74C3C;");
            e.printStackTrace();
        }
    }

    // Old placeholder button action for updating status
    @FXML
    public void handleUpdateStatus(ActionEvent event) {
        lblStatus.setText("Update Status screen coming soon.");
        lblStatus.setStyle("-fx-text-fill: white;");
    }

    // Logs out and goes back to the main welcome screen
    @FXML
    public void handleLogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/MainView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    // Navigation button for the create screen
    // Doesn't need to do anything since the user is already on this screen
    @FXML
    public void handleNavCreate(ActionEvent event) {
        System.out.println("Already on the Create Ticket screen.");
    }

    // Switches to the update status screen
    @FXML
    public void handleNavUpdate(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/UpdateStatusView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 850, 600));
        stage.show();
    }

    // Goes back to the main admin dashboard screen
    @FXML
    public void handleNavDashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/cts/protechsolutionsjavafxapp/AdminDashboardView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Resize back to the original dashboard dimensions
        stage.setScene(new Scene(root, 750, 500));
        stage.show();
    }
}
