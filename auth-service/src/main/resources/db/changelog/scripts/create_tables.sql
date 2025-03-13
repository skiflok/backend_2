CREATE SCHEMA IF NOT EXISTS s21;

CREATE SEQUENCE IF NOT EXISTS s21.users_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE s21.users
(
    id              BIGINT NOT NULL default nextval('s21.users_id_seq'),
    email           VARCHAR(255),
    first_name      VARCHAR(255),
    last_name       VARCHAR(255),
    phone_number    VARCHAR(20),
    password        VARCHAR(255),
    PRIMARY KEY     (id)
);
