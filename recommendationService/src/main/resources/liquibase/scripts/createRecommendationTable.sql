-- liquibase formatted sql
-- changeset formatted sryazanova:1

CREATE TABLE recommendations(
    Id UUID,
    product_name TEXT,
    product_id uuid,
    product_text TEXT,
    rules TEXT
);