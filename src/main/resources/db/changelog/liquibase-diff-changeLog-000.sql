--liquibase formatted sql

--changeset gilberto:000.00:
CREATE SEQUENCE vehicle_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 2;
CREATE TABLE SGV_VEHICLE (
    ID NUMERIC(19,0) NOT NULL,
    BRAND VARCHAR(100) NOT NULL,
    MODEL VARCHAR(100) NOT NULL,
    LICENSE_PLATE VARCHAR(100) NOT NULL,
    NUMBER_OF_SEATS NUMERIC(19,0) NOT NULL,
    CONSTRAINT SGV_VEHICLE_PK PRIMARY KEY (ID)
);
--rollback DROP TABLE SGV_VEHICLE;
--rollback DROP SEQUENCE vehicle_id_seq;


--changeset gilberto:000.01:
CREATE SEQUENCE address_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 2;
CREATE TABLE SGV_ADDRESS (
    ID NUMERIC(19,0) NOT NULL,
    CEP VARCHAR(100) NOT NULL,
    STREET VARCHAR(100) NOT NULL,
    NEIGHBORHOOD VARCHAR(100) NOT NULL,
    CITY VARCHAR(100) NOT NULL,
    NUMBER NUMERIC(19,0) NOT NULL,
    CONSTRAINT SGV_ADDRESS_PK PRIMARY KEY (ID)
);
--rollback DROP TABLE SGV_ADDRESS;
--rollback DROP SEQUENCE address_id_seq;


--changeset gilberto:000.02:
CREATE SEQUENCE institution_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 2;
CREATE TABLE SGV_INSTITUTION (
    ID NUMERIC(19,0) NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    ADDRESS NUMERIC(19,0) NOT NULL,
    CONSTRAINT SGV_INSTITUTION_PK PRIMARY KEY (ID)
);

ALTER TABLE SGV_INSTITUTION ADD CONSTRAINT FK_INSTITUTION_ADDRESS FOREIGN KEY (ADDRESS) REFERENCES SGV_ADDRESS(ID);

--rollback DROP TABLE SGV_INSTITUTION;
--rollback DROP SEQUENCE institution_id_seq;


--changeset gilberto:000.03:
CREATE SEQUENCE passenger_info_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 2;
CREATE TABLE SGV_PASSENGER_INFO (
    ID NUMERIC(19,0) NOT NULL,
    INSTITUTION NUMERIC(19,0) NOT NULL,
    CONSTRAINT SGV_PASSENGER_PK PRIMARY KEY (ID)
);

ALTER TABLE SGV_PASSENGER_INFO ADD CONSTRAINT FK_PASSENGER_INSTITUTION FOREIGN KEY (INSTITUTION) REFERENCES SGV_INSTITUTION(ID);

--rollback DROP TABLE SGV_PASSENGER_INFO;
--rollback DROP SEQUENCE passenger_info_id_seq;


--changeset gilberto:000.04:
CREATE SEQUENCE driver_info_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 2;
CREATE TABLE SGV_DRIVER_INFO (
    ID NUMERIC(19,0) NOT NULL,
    VEHICLE NUMERIC(19,0) NOT NULL,
    CONSTRAINT SGV_DRIVER_PK PRIMARY KEY (ID)
);

ALTER TABLE SGV_DRIVER_INFO ADD CONSTRAINT FK_DRIVER_VEHICLE FOREIGN KEY (VEHICLE) REFERENCES SGV_VEHICLE(ID);

--rollback DROP TABLE SGV_DRIVER_INFO;
--rollback DROP SEQUENCE driver_info_id_seq;


--changeset gilberto:000.05:
CREATE SEQUENCE user_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 2;
CREATE TABLE SGV_USER (
    ID NUMERIC(19,0) NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    PASSWORD VARCHAR(100) NOT NULL,
    PHONE VARCHAR(100) NOT NULL,
    CPF VARCHAR(100) NOT NULL,
    ROLE VARCHAR(100) NOT NULL,
    ADDRESS NUMERIC(19,0) NOT NULL,
    PASSENGER_INFO NUMERIC(19,0),
    DRIVER_INFO NUMERIC(19,0),
    CONSTRAINT SGV_USER_PK PRIMARY KEY (ID)
);

ALTER TABLE SGV_USER ADD CONSTRAINT FK_USER_PASSENGER_INFO FOREIGN KEY (PASSENGER_INFO) REFERENCES SGV_PASSENGER_INFO(ID);
ALTER TABLE SGV_USER ADD CONSTRAINT FK_USER_DRIVER_INFO FOREIGN KEY (DRIVER_INFO) REFERENCES SGV_DRIVER_INFO(ID);

--rollback DROP TABLE SGV_USER;
--rollback DROP SEQUENCE user_id_seq;

--changeset gilberto:000.06:
CREATE SEQUENCE route_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 2;
CREATE TABLE SGV_ROUTE (
    ID NUMERIC(19,0) NOT NULL,
    DESCRIPTION VARCHAR(100) NOT NULL,
    PERIOD VARCHAR(100) NOT NULL,
    DRIVER_INFO NUMERIC(19,0) NOT NULL,
    INSTITUTION NUMERIC(19,0) NOT NULL,
    CONSTRAINT SGV_ROUTE_PK PRIMARY KEY (ID)
);

ALTER TABLE SGV_ROUTE ADD CONSTRAINT FK_ROUTE_DRIVER_INFO FOREIGN KEY (DRIVER_INFO) REFERENCES SGV_DRIVER_INFO(ID);
ALTER TABLE SGV_ROUTE ADD CONSTRAINT FK_ROUTE_INSTITUTION FOREIGN KEY (INSTITUTION) REFERENCES SGV_INSTITUTION(ID);

--rollback DROP TABLE SGV_ROUTE;
--rollback DROP SEQUENCE route_id_seq;


--changeset gilberto:000.07:
CREATE TABLE SGV_ROUTE_PASSENGER (
	ID_ROUTE NUMERIC(19) NOT NULL,
	ID_PASSENGER_INFO NUMERIC(19) NOT NULL,
	CONSTRAINT SGV_ROUTE_PASSENGER_PK PRIMARY KEY (ID_ROUTE, ID_PASSENGER_INFO)
);

ALTER TABLE SGV_ROUTE_PASSENGER ADD CONSTRAINT FK_RP_ROUTE FOREIGN KEY (ID_ROUTE) REFERENCES SGV_ROUTE(ID);
ALTER TABLE SGV_ROUTE_PASSENGER ADD CONSTRAINT FK_RP_PASSENGER FOREIGN KEY (ID_PASSENGER_INFO) REFERENCES SGV_PASSENGER_INFO(ID);








