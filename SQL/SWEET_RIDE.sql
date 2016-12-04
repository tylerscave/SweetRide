DROP DATABASE IF EXISTS SWEET_RIDE;
CREATE DATABASE SWEET_RIDE;
USE SWEET_RIDE;


DROP TABLE IF EXISTS customer;
CREATE TABLE customer
(
 c_id INT AUTO_INCREMENT,
 first_name VARCHAR(50) NOT NULL,
 last_name VARCHAR(50) NOT NULL,
 email VARCHAR(50) UNIQUE NOT NULL,
 pwd VARCHAR(50) NOT NULL,
 PRIMARY KEY (c_id)
);
ALTER table customer AUTO_INCREMENT = 1001;


DROP TABLE IF EXISTS vehicle;
CREATE TABLE vehicle
(
 v_id INT AUTO_INCREMENT,
 year INT NOT NULL,
 make VARCHAR(50) NOT NULL,
 model VARCHAR(50) NOT NULL,
 reserved BOOLEAN DEFAULT FALSE,
 PRIMARY KEY (v_id)
);
ALTER table vehicle AUTO_INCREMENT = 1001;


DROP TABLE IF EXISTS reservation;
CREATE TABLE reservation
(
 c_id INT NOT NULL,
 v_id INT NOT NULL,
 start_date DATE DEFAULT '0000-00-00',
 end_date DATE DEFAULT '0000-00-00',
 overdue BOOLEAN DEFAULT FALSE,
 FOREIGN KEY (c_id) REFERENCES customer (c_id),
 FOREIGN KEY (v_id) REFERENCES vehicle (v_id),
 PRIMARY KEY(c_id, v_id)
);


DROP TABLE IF EXISTS location;
CREATE TABLE location
(
 l_id INT AUTO_INCREMENT,
 location_name VARCHAR(50),
 street VARCHAR(50) NOT NULL,
 city VARCHAR(50) NOT NULL,
 state VARCHAR(50) NOT NULL,
 zip INT NOT NULL,
 PRIMARY KEY (l_id)
);

DROP TABLE IF EXISTS customer_location;
CREATE TABLE customer_location
(
 c_id INT NOT NULL,
 l_id INT NOT NULL,
 FOREIGN KEY (c_id) REFERENCES customer (c_id),
 FOREIGN KEY (l_id) REFERENCES location (l_id)
);

DROP TABLE IF EXISTS vehicle_location;
CREATE TABLE vehicle_location
(
 v_id INT NOT NULL,
 l_id INT NOT NULL,
 FOREIGN KEY (v_id) REFERENCES vehicle (v_id),
 FOREIGN KEY (l_id) REFERENCES location (l_id)
);


DROP TABLE IF EXISTS class;
CREATE TABLE class
(
 class_id INT NOT NULL,
 class_type VARCHAR(50) NOT NULL,
 PRIMARY KEY (class_id)
);

INSERT INTO class (`class_id`, `class_type`) VALUES
(1, 'COMPACT'),
(2, 'SPORT'),
(3, 'LUXURY'),
(4, 'SUV'),
(5, 'TRUCK');

DROP TABLE IF EXISTS vehicle_class;
CREATE TABLE vehicle_class
(
 v_id INT NOT NULL,
 class_id INT NOT NULL,
 FOREIGN KEY (v_id) REFERENCES vehicle (v_id),
 FOREIGN KEY (class_id) REFERENCES class (class_id)
);


DROP TABLE IF EXISTS transmission;
CREATE TABLE transmission
(
 trans_id INT NOT NULL,
 trans_type VARCHAR(50) NOT NULL,
 PRIMARY KEY (trans_id)
);

INSERT INTO transmission (`trans_id`, `trans_type`) VALUES
(1, 'MANUAL'),
(2, 'AUTOMATIC');

DROP TABLE IF EXISTS vehicle_transmission;
CREATE TABLE vehicle_transmission
(
 v_id INT NOT NULL,
 trans_id INT NOT NULL,
 FOREIGN KEY (v_id) REFERENCES vehicle (v_id),
 FOREIGN KEY (trans_id) REFERENCES transmission (trans_id)
);


DROP TABLE IF EXISTS admin;
CREATE TABLE admin
(
 email VARCHAR(50) NOT NULL,
 pwd VARCHAR(50) NOT NULL,
 PRIMARY KEY (email)
);

INSERT INTO admin (`email`, `pwd`) VALUES
('admin@sweetride.com', 'admin');


DROP TABLE IF EXISTS reservationArchive;
CREATE TABLE reservationArchive
(
	archiveEntry INT auto_increment,
	c_id INT NOT NULL,
	v_id INT NOT NULL,
	start_date DATE DEFAULT '0000-00-00',
	end_date DATE DEFAULT '0000-00-00',
	overdue BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (archiveEntry)
);
ALTER TABLE reservationArchive auto_increment=1;

DELIMITER //
DROP PROCEDURE IF EXISTS archive_reservation//
CREATE PROCEDURE archive_reservation(IN archiveDate Date)
BEGIN
  INSERT INTO reservationArchive(c_id, v_id, start_date, end_date, overdue)
	SELECT * FROM reservation WHERE reservation.end_date < archiveDate;
    SET SQL_SAFE_UPDATES = 0;
  DELETE FROM reservation
	WHERE end_date < archiveDate;
    SET SQL_SAFE_UPDATES = 1;
END//
DELIMITER ;

DROP TRIGGER IF EXISTS DeleteVehicleTrigger;
delimiter //
CREATE TRIGGER DeleteVehicleTrigger
AFTER DELETE ON vehicle FOR EACH ROW
BEGIN
delete from reservation where v_id =Old.v_id;
END;//
delimiter ;


DROP TRIGGER IF EXISTS DeleteUserTrigger;
delimiter //
CREATE TRIGGER DeleteUserTrigger
AFTER DELETE ON customer FOR EACH ROW
BEGIN
delete from reservation where c_id =Old.c_id;
END;//
delimiter ;


LOAD DATA LOCAL INFILE '~/javaWorkspace/SweetRide/SQL/vehicle.txt' INTO TABLE vehicle;
LOAD DATA LOCAL INFILE '~/javaWorkspace/SweetRide/SQL/location.txt' INTO TABLE location;
LOAD DATA LOCAL INFILE '~/javaWorkspace/SweetRide/SQL/vehicle_location.txt' INTO TABLE vehicle_location;
LOAD DATA LOCAL INFILE '~/javaWorkspace/SweetRide/SQL/vehicle_class.txt' INTO TABLE vehicle_class;
LOAD DATA LOCAL INFILE '~/javaWorkspace/SweetRide/SQL/vehicle_transmission.txt' INTO TABLE vehicle_transmission;
