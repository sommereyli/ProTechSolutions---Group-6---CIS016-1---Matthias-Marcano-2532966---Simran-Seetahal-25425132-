# Device Repair Tracking System (DRTS)
**ProTechSolutions - "Repair with Care"**

**Unit:** CIS016-1 / CIS096-1 – Principles of Programming & Data Structures  
**Group 6 Members:** 
* Matthias Marcano (ID: 2532966) - Backend & Logic Lead
* Simran Seetahal (ID: 25425132) - UI & Testing Lead

---

## Project Overview
The Device Repair Tracking System (DRTS) is a 2-tier Java application designed to digitize and streamline the repair workflow for local electronics shops. Built on an **MVC architecture**, the system replaces manual paper logs with a centralized MySQL database. It features secure staff authentication, automated cost calculation, and a customer-facing portal for real-time status tracking.

## Tech Stack
* **Language:** Java (JDK 23)
* **Frontend:** JavaFX & FXML
* **Database:** MySQL
* **Connectivity:** JDBC (Java Database Connectivity)
* **Build Tool:** Maven

---

## Setup & Installation Instructions (For Marking)

To run this application locally, please follow these steps:

### 1. Database Configuration
The application requires a local MySQL server (e.g., via XAMPP or MySQL Workbench).
1. Open your MySQL management tool.
2. Locate the `database_setup.sql` file in the root directory of this repository.
3. Execute the SQL script. This will automatically:
   * Create a database named `protech_db`.
   * Create the `tickets` table.
   * Insert sample data (including Ticket IDs `PRO-3743` and `PRO-6665` for immediate testing).

*Note: The `DatabaseHandler.java` class is pre-configured to use the default local MySQL credentials (`username: root`, `password: ""`).*

### 2. Running the Application
1. Clone or download this repository to your local machine.
2. Open the project in **IntelliJ IDEA**.
3. Reload the Maven project (this will automatically download the required JavaFX and MySQL Connector-J dependencies from the `pom.xml`).
4. Navigate to `src/main/java/com/cts/protechsolutionsjavafxapp/App.java`.
5. Run `App.java` to launch the application.

---

## Testing Credentials

To test the **Administrator/Technician** functionalities, please use the following hardcoded credentials on the Login screen:
* **Username:** admin
* **Password:** 123

To test the **Customer Search** functionality, you do not need to log in. Simply click "Enter Unique Ticket ID" on the welcome screen and search for one of the pre-loaded tickets:
* `PRO-3743`
* `PRO-6665`

---

