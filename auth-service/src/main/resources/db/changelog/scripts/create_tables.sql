CREATE SCHEMA IF NOT EXISTS s21;

CREATE TABLE s21.users
(
    id              BIGSERIAL NOT NULL,
    email           VARCHAR(255) UNIQUE,
    first_name      VARCHAR(255),
    last_name       VARCHAR(255),
    phone_number    VARCHAR(20),
    password        VARCHAR(255),
    PRIMARY KEY     (id)
);
