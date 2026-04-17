package com.cts.protechsolutionsjavafxapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class manages the connection between the Java application and the MySQL database
public class DatabaseHandler {

    // Database connection details including the server address and database name
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/protech_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql";

    // Method to establish and return a connection to the database
    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL driver so the application can communicate with the database
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Return the connection using the defined URL and credentials
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Error message if the driver is missing from the project libraries
            System.out.println("MySQL Driver not found!");
            e.printStackTrace();
            return null;
        }
    }
}
