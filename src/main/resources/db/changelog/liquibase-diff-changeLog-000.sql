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
CREATE SEQUENCE passanger_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 2;
CREATE TABLE SGV_PASSANGER (
    ID NUMERIC(19,0) NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    PHONE VARCHAR(100) NOT NULL,
    CPF VARCHAR(100) NOT NULL,
    ADDRESS NUMERIC(19,0) NOT NULL,
    INSTITUTION NUMERIC(19,0) NOT NULL,
    CONSTRAINT SGV_INSTITUTION_PK PRIMARY KEY (ID)
);

ALTER TABLE SGV_PASSANGER ADD CONSTRAINT FK_PASSANGER_INSTITUTION FOREIGN KEY (INSTITUTION) REFERENCES SGV_INSTITUTION(ID);
ALTER TABLE SGV_PASSANGER ADD CONSTRAINT FK_PASSANGER_ADDRESS FOREIGN KEY (ADDRESS) REFERENCES SGV_ADDRESS(ID);

--rollback DROP TABLE SGV_PASSANGER;
--rollback DROP SEQUENCE passanger_id_seq;


--changeset gilberto:000.04:
CREATE SEQUENCE driver_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 2;
CREATE TABLE SGV_DRIVER (
    ID NUMERIC(19,0) NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    PHONE VARCHAR(100) NOT NULL,
    CPF VARCHAR(100) NOT NULL,
    ADDRESS NUMERIC(19,0) NOT NULL,
    VEHICLE NUMERIC(19,0) NOT NULL,
    CONSTRAINT SGV_DRIVER_PK PRIMARY KEY (ID)
);

ALTER TABLE SGV_DRIVER ADD CONSTRAINT FK_DRIVER_VEHICLE FOREIGN KEY (VEHICLE) REFERENCES SGV_VEHICLE(ID);
ALTER TABLE SGV_DRIVER ADD CONSTRAINT FK_DRIVER_ADDRESS FOREIGN KEY (ADDRESS) REFERENCES SGV_ADDRESS(ID);

--rollback DROP TABLE SGV_DRIVER;
--rollback DROP SEQUENCE driver_id_seq;


--changeset gilberto:000.06:
CREATE SEQUENCE route_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 2;
CREATE TABLE SGV_ROUTE (
    ID NUMERIC(19,0) NOT NULL,
    DESCRIPTION VARCHAR(100) NOT NULL,
    PERIOD VARCHAR(100) NOT NULL,
    DRIVER NUMERIC(19,0) NOT NULL,
    INSTITUTION NUMERIC(19,0) NOT NULL,
    CONSTRAINT SGV_ROUTE_PK PRIMARY KEY (ID)
);

ALTER TABLE SGV_ROUTE ADD CONSTRAINT FK_ROUTE_DRIVER FOREIGN KEY (DRIVER) REFERENCES SGV_DRIVER(ID);
ALTER TABLE SGV_ROUTE ADD CONSTRAINT FK_ROUTE_INSTITUTION FOREIGN KEY (INSTITUTION) REFERENCES SGV_INSTITUTION(ID);

--rollback DROP TABLE SGV_ROUTE;
--rollback DROP SEQUENCE route_id_seq;


--changeset gilberto:000.07:
CREATE TABLE SGV_ROUTE_PASSANGER (
	ID_ROUTE NUMERIC(19) NOT NULL,
	ID_PASSAGER NUMERIC(19) NOT NULL,
	CONSTRAINT SGV_ROUTE_PASSANGER_PK PRIMARY KEY (ID_ROUTE, ID_PASSAGER)
);

ALTER TABLE SGV_ROUTE_PASSANGER ADD CONSTRAINT FK_RP_ROUTE FOREIGN KEY (ID_ROUTE) REFERENCES SGV_ROUTE(ID);
ALTER TABLE SGV_ROUTE_PASSANGER ADD CONSTRAINT FK_RP_PASSAGER FOREIGN KEY (ID_PASSAGER) REFERENCES SGV_PASSAGER(ID);








