CREATE SCHEMA IF NOT EXISTS s21;

CREATE SEQUENCE IF NOT EXISTS s21.address_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS s21.clients_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS s21.image_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS s21.product_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS s21.supplier_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE s21.address
(
    id      BIGINT NOT NULL default nextval('s21.address_id_seq'),
    country VARCHAR(255),
    city    VARCHAR(255),
    street  VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE s21.image
(
    id    UUID NOT NULL,
    image bytea,
    PRIMARY KEY (id)
);

CREATE TABLE s21.client
(
    id                BIGINT NOT NULL default nextval('s21.clients_id_seq'),
    client_name       VARCHAR(255),
    client_surname    VARCHAR(255),
    birthday          DATE,
    gender            VARCHAR(255) CHECK (gender IN ('MALE', 'FEMALE')),
    registration_date DATE,
    address_id        BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES s21.address (id)
);

CREATE TABLE s21.supplier
(
    id           BIGINT NOT NULL default nextval('s21.supplier_id_seq'),
    name         VARCHAR(255),
    address_id   BIGINT,
    phone_number VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES s21.address (id)
);

create type s21.category as enum ('ELECTRONICS', 'CLOTHING', 'COSMETICS', 'HOME_AND_GARDEN', 'SPORTS_AND_OUTDOORS');

CREATE TABLE s21.product
(
    id               BIGINT NOT NULL default nextval('s21.product_id_seq'),
    name             VARCHAR(255),
    category         s21.category,
    price            NUMERIC(38, 2),
    available_stock  INTEGER,
    last_update_date DATE,
    supplier_id      BIGINT,
    image_id         UUID,
    PRIMARY KEY (id),
    FOREIGN KEY (image_id) REFERENCES s21.image (id),
    FOREIGN KEY (supplier_id) REFERENCES s21.supplier (id)
);
