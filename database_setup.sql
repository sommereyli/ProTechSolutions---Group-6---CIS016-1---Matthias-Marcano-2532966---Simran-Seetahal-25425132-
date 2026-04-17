CREATE DATABASE IF NOT EXISTS protech_db;
USE protech_db;

CREATE TABLE IF NOT EXISTS tickets (
    ticket_id VARCHAR(20) PRIMARY KEY,
    customer_name VARCHAR(100),
    phone_number VARCHAR(20),
    device_info VARCHAR(100),
    device_model VARCHAR(100),
    fault_description TEXT,
    repair_status VARCHAR(50),
    parts_cost DOUBLE DEFAULT 0.0,
    labor_cost DOUBLE DEFAULT 0.0
);

INSERT INTO tickets (ticket_id, customer_name, phone_number, device_info, device_model, fault_description, repair_status, parts_cost, labor_cost) 
VALUES 
('PRO-3743', 'Matthias Marcano', '730-0966', 'Samsung', 'S22 Ultra', 'Busted Screen', 'Completed', 25.0, 15.0),
('PRO-6665', 'Simran Seetahal', '765-4321', 'Iphone', '25 Pro Max', 'Touch screen not registering', 'In Queue', 0.0, 0.0);
