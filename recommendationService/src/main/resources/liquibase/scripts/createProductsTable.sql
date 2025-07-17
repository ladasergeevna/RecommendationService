-- liquibase formatted sql
-- changeset formatted sryazanova:5

CREATE TABLE products (
    id UUID PRIMARY KEY,
    type TEXT NOT NULL
);