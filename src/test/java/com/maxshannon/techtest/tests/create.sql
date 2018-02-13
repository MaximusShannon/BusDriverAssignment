CREATE TABLE driver (
  ID int(11) NOT NULL,
  DRIVERID int(11) DEFAULT NULL,
  FIRSTNAME varchar(255) DEFAULT NULL,
  LASTNAME varchar(255) DEFAULT NULL,
  MIDDLEINITIAL varchar(255) DEFAULT NULL,
  OPER_CLASS varchar(255) DEFAULT NULL,
  VERSION int(11) DEFAULT NULL,
  LOCATION_ID int(11) DEFAULT NULL,
  CARRIER_ID int(11) DEFAULT NULL,
  PRIMARY KEY (ID)

);

CREATE TABLE location (
  ID int(11) NOT NULL,
  LOCATIONID bigint(20) DEFAULT NULL,
  LOCATIONNAME varchar(255) DEFAULT NULL,
  VERSION int(11) DEFAULT NULL,
  DRIVER_ID int(11) DEFAULT NULL,
  PRIMARY KEY (ID)

);

CREATE TABLE carrier (
  ID int(11) NOT NULL,
  CARRIERNAME varchar(255) DEFAULT NULL,
  VERSION int(11) DEFAULT NULL,
  DRIVER_ID int(11) DEFAULT NULL,
  PRIMARY KEY (ID)
) ;

ALTER TABLE driver
    ADD FOREIGN KEY (LOCATION_ID) REFERENCES Location(ID);
ALTER TABLE driver
    ADD  FOREIGN KEY (CARRIER_ID) REFERENCES carrier(ID);
ALTER TABLE location
    ADD FOREIGN KEY (DRIVER_ID) REFERENCES Driver(ID);
ALTER TABLE carrier
    ADD  FOREIGN KEY (DRIVER_ID) REFERENCES Driver(ID);


