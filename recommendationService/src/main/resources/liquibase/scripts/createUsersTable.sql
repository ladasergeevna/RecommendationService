-- liquibase formatted sql
-- changeset formatted sryazanova:3

CREATE TABLE users_deposits(
    user_id UUID,
    debit_amount DECIMAL(10,2),
    saving_amount DECIMAL(10,2),
    credit_amount DECIMAL(10,2),
    invest_amount DECIMAL(10,2)
);

CREATE TABLE users_withdraws (
    user_id UUID,
    debit_amount DECIMAL(10,2),
    saving_amount DECIMAL(10,2),
    credit_amount DECIMAL(10,2),
    invest_amount DECIMAL(10,2)
);

create table transactions(
id SERIAL PRIMARY KEY,
    type TEXT,
    user_id UUID,
    product_id UUID
)