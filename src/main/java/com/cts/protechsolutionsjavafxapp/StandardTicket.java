package com.cts.protechsolutionsjavafxapp;

// StandardTicket class that extends the base RepairTicket
// This handles the specific cost details for a regular repair job
public class StandardTicket extends RepairTicket {

    // Variables to track the cost of components and the work performed
    private double partsCost;
    private double laborCost;

    // Constructor to set up the ticket with the ID, customer, and device details
    public StandardTicket(String ticketID, Customer customer, Device device) {
        // Passes the main info to the parent RepairTicket class
        super(ticketID, customer, device);

        // Starts the costs at zero until the repair details are added
        this.partsCost = 0.0;
        this.laborCost = 0.0;
    }

    // Updates the cost for any parts used in the repair
    public void setPartsCost(double partsCost) {
        this.partsCost = partsCost;
    }

    // Updates the cost for the labor time spent on the device
    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
    }

    // Calculates the total cost of the repair by adding parts and labor together
    @Override
    public double calculateTotal() {
        return partsCost + laborCost;
    }
}
