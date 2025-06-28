-- liquibase formatted sql
-- changeset formatted sryazanova:2

CREATE TABLE rules(
    id UUID PRIMARY KEY,
    query TEXT,
    negate BOOLEAN,
    product_id UUID
);