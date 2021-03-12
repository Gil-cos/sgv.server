--liquibase formatted sql

--changeset gilbejun:002:
ALTER TABLE SGV_ADDRESS ALTER COLUMN NUMBER TYPE varchar USING NUMBER::varchar;

