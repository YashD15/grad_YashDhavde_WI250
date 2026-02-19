-- CREATE DATABASE studentdbrest;

CREATE TABLE students (
    regno TEXT PRIMARY KEY,
    rollno TEXT,
    name TEXT,
    standard INT,
    school TEXT,
    gender VARCHAR(6),
    percentage DECIMAL(5,2)
);

TRUNCATE TABLE students;
DROP TABLE students;

SELECT * FROM students;

-- DUMMY DATA TO TEST
INSERT INTO students (regno, rollno, name, standard, school, gender, percentage) VALUES
('R101', '1', 'Arjun Sharma', 5, 'KV', 'MALE', 85.50),
('R102', '2', 'Priya Verma', 5, 'KV', 'FEMALE', 78.20),
('R103', '3', 'Rahul Mehta', 6, 'DPS', 'MALE', 39.00),
('R104', '4', 'Sneha Reddy', 6, 'DPS', 'FEMALE', 91.40),
('R105', '5', 'Amit Kumar', 5, 'KV', 'MALE', 67.80),
('R106', '6', 'Neha Singh', 7, 'DPS', 'FEMALE', 45.60),
('R107', '7', 'Rohan Das', 7, 'KV', 'MALE', 32.50),
('R108', '8', 'Anjali Patel', 5, 'DPS', 'FEMALE', 88.90),
('R109', '9', 'Vikram Joshi', 6, 'KV', 'MALE', 55.75),
('R110', '10', 'Kavya Nair', 5, 'DPS', 'FEMALE', 40.00);

