module com.cts.protechsolutionsjavafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens com.cts.protechsolutionsjavafxapp to javafx.fxml;
    exports com.cts.protechsolutionsjavafxapp;
}