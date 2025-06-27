-- liquibase formatted sql
-- changeset formatted sryazanova:2

CREATE TABLE rules(
    Id UUID,
    query TEXT,
    arguments TEXT,
    negate boolean,
    product_id UUID
);