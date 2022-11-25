-- Database: exhibitions
-- Execution: psql -U postgres -f <path>

-- Clear if already exists
DROP DATABASE IF EXISTS exhibitions_project (FORCE);

-- Drop a table
DROP TABLE exhibitions;

-- Create DB
CREATE DATABASE exhibitions_project
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- Comment the DB
COMMENT ON DATABASE exhibitions_project
    IS 'final project exhibition database';
	
-- Connect to exhibitions_project DB 
\c exhibitions_project

-- Create table users
CREATE TABLE users(
	user_id SERIAL PRIMARY KEY NOT NULL,
	first_name TEXT NOT NULL,
	last_name TEXT NOT NULL,
	nickname TEXT NOT NULL,
	password TEXT NOT NULL,
	role TEXT NOT NULL
);

-- Create table exhibitions
CREATE TABLE exhibitions(
	exhibition_id SERIAL PRIMARY KEY NOT NULL,
	theme TEXT NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	opening_time TIME NOT NULL,
	closing_time TIME NOT NULL,
	ticket_price NUMERIC(10,2) NOT NULL,
	state BOOLEAN NOT NULL DEFAULT TRUE
);

-- Create table halls
CREATE TABLE halls(
	hall_id SERIAL PRIMARY KEY NOT NULL,
	hall_name TEXT NOT NULL,
	hall_city TEXT,
	hall_country TEXT
);

-- Create table exhibitions_halls
CREATE TABLE exhibitions_halls(
	exhibition_hall_id SERIAL PRIMARY KEY NOT NULL,
	exhibition_id SERIAL REFERENCES exhibitions (exhibition_id),
	hall_id SERIAL REFERENCES halls (hall_id)
);

-- Create table purchases
CREATE TABLE purchases(
	purchase_id SERIAL PRIMARY KEY NOT NULL,
	user_id SERIAL REFERENCES users (user_id),
	exhibition_id SERIAL REFERENCES exhibitions (exhibition_id),
	purchase_date DATE NOT NULL
);

-- List tables of exhibitions_project DB (need to be already connected)
\dt

-- Describe tables users, exhibitions, exhibitions_halls, halls, purchases (need to be already connected)
\dt+ users
\dt+ exhibitions
\dt+ exhibitions_halls
\dt+ halls
\dt+ purchases

-- Some data to insert
INSERT INTO exhibitions (theme, start_date, end_date, opening_time, closing_time, ticket_price) VALUES
('Movie1', '2020-12-13', '2020-12-16', '15:35', '20:30', 250.20),
('Movie2', '2021-06-22', '2021-09-20', '11:30', '17:00', 55.10);

INSERT INTO exhibitions (theme, start_date, end_date, opening_time, closing_time, ticket_price) VALUES
('Movie3', '2022-12-13', '2022-12-16', '14:35', '21:30', 24.50);

INSERT INTO users (first_name, last_name, nickname, password, role) VALUES
('John', 'Stoner', 'Jstoner', '12345', 'Admin'),
('Frank', 'Mitch', 'FMtch', '12345', 'User');

INSERT INTO purchases (user_id, exhibition_id, purchase_date) VALUES
(2,1, '2020-12-15'),
(2,2, '2021-09-14'),
(1,1, '2020-02-13');

INSERT INTO halls (hall_name, hall_city, hall_country) VALUES
('Fine Art America', 'Mexico City', 'Mexico'),
('Hampton Roads Convention Center', 'Boston', 'United States'),
('Pixels', 'London', 'United Kingdom');
	
INSERT INTO exhibitions_halls (exhibition_id, hall_id) VALUES
(1, 3),
(1, 2),
(2, 2);

-- Query list all exhibitions with their halls
SELECT a.exhibition_id, b.hall_id, c.hall_name FROM exhibitions a JOIN exhibitions_halls b ON (a.exhibition_id=b.exhibition_id) JOIN halls c ON (b.hall_id=c.hall_id);

-- Quey list active exhibitions filter by date
SELECT a.*, b.hall_id, c.hall_name FROM exhibitions a JOIN exhibitions_halls b ON (a.exhibition_id=b.exhibition_id) JOIN halls c ON (b.hall_id=c.hall_id) WHERE a.state=TRUE ORDER BY a.start_date;

	