insert into s21.address (city, country, street)
values ('Moscow', 'RUSSIA', 'qwerty'),
       ('Kazan', 'RUSSIA', 'asdfgh');

INSERT INTO s21.image (id, image)
VALUES (gen_random_uuid(), pg_read_binary_file('/psql_map_folder/product_images/duck.PNG'));

INSERT INTO s21.client (client_name,
                        client_surname,
                        birthday,
                        gender,
                        registration_date,
                        address_id)
VALUES ('client 1', 'client_surname 1', '2000-10-11', 'MALE', '2022-09-05', 1),
       ('client 2', 'client_surname 2', '2001-12-15', 'FEMALE', '2022-09-06', 2);

INSERT INTO s21.supplier ("name", address_id, phone_number)
VALUES ('pppppp', 1, '8987654321');

INSERT INTO s21.product ("name", "category", price, available_stock, last_update_date, supplier_id, image_id)
VALUES ('duck', 'HOME_AND_GARDEN', 15, 1000, '2024-10-15', 1, (SELECT id FROM s21.image LIMIT 1)),
       ('duck2', 'HOME_AND_GARDEN', 15, 1000, '2024-10-15', 1, (SELECT id FROM s21.image LIMIT 1));
