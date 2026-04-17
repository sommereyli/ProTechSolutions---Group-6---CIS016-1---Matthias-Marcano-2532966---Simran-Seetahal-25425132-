package com.cts.protechsolutionsjavafxapp;

// Customer class extending the main User class
// Holds specific details for people dropping off devices for repair
public class Customer extends User {

    // Optional ID just in case it's needed later for database lookups
    private String customerID;

    // Constructor to set up a new customer
    public Customer(String name, String phoneNumber) {
        // super() passes the name and phone number up to the parent User class to handle those fields
        super(name, phoneNumber);
    }

    // Gets the customer ID
    public String getCustomerID() {
        return customerID;
    }

    // Sets or updates the customer ID
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}
