package com.cts.protechsolutionsjavafxapp;

// This class represents the physical device that a customer brings in for repair
public class Device {
    // Variables to store the brand, model, and what is wrong with the device
    private String brand;
    private String model;
    private String faultDescription;

    // Constructor to initialize the device details when a new record is created
    public Device(String brand, String model, String faultDescription) {
        this.brand = brand;
        this.model = model;
        this.faultDescription = faultDescription;
    }

    // Returns the brand of the device
    public String getBrand() { return brand; }

    // Returns the specific model of the device
    public String getModel() { return model; }

    // Returns the description of the fault or issue reported by the customer
    public String getFaultDescription() { return faultDescription; }
}
