package com.cts.protechsolutionsjavafxapp;

// Abstract base class that (was supposed to) serves as the foundation for both staff and customers
// Defines common attributes that all users in the system share
public abstract class User {

    // Private variables to store the user's name and contact information
    private String name;
    private String phoneNumber;

    // Constructor to initialize the name and phone number when a user object is created
    public User(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Returns the name of the user
    public String getName() {
        return name;
    }

    // Updates the name of the user
    public void setName(String name) {
        this.name = name;
    }

    // Returns the phone number of the user
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Updates the phone number of the user
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
