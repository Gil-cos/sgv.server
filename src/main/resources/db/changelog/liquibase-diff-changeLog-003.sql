--liquibase formatted sql

--changeset gilbejun:003:
ALTER TABLE SGV_ROUTE ADD SHARED_LOCATION_LINK VARCHAR(200);

