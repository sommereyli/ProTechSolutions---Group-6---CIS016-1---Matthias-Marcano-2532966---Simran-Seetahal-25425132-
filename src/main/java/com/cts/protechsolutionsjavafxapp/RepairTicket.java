package com.cts.protechsolutionsjavafxapp;

// Abstract base class representing a general repair ticket
// This class uses composition by including the Customer and Device objects
public abstract class RepairTicket {
    private String ticketID;
    private String status;

    // Linking the ticket to a specific customer and their device
    private Customer customer;
    private Device device;

    // Constructor to initialize ticket details and set the starting status
    public RepairTicket(String ticketID, Customer customer, Device device) {
        this.ticketID = ticketID;
        this.customer = customer;
        this.device = device;
        this.status = "In Queue"; // All new tickets start with this status
    }

    // Abstract method to calculate the total cost
    // Subclasses will provide their own specific logic for this
    public abstract double calculateTotal();

    // Returns the unique ticket ID
    public String getTicketID() { return ticketID; }

    // Returns the current repair status
    public String getStatus() { return status; }

    // Updates the repair status
    public void setStatus(String status) { this.status = status; }

    // Returns the customer object associated with this ticket
    public Customer getCustomer() { return customer; }

    // Returns the device object associated with this ticket
    public Device getDevice() { return device; }
}
