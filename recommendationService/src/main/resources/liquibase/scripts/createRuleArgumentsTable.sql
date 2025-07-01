-- liquibase formatted sql
-- changeset you:rule-args-uuid

CREATE TABLE rule_arguments (
    rule_id UUID NOT NULL,
    argument TEXT,
    FOREIGN KEY (rule_id) REFERENCES rules(id)
);