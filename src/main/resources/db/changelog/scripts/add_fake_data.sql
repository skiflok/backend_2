insert into s21.address (city, country, street)
values ('Moscow', 'RUSSIA', 'qwerty'),
       ('Kazan', 'RUSSIA', 'asdfgh');

select * from s21.address;

-- INSERT INTO s21.image (id, image)
-- VALUES (gen_random_uuid(), pg_read_binary_file(''));


-- INSERT INTO s21.client (client_name,
--                         client_surname,
--                         birthday,
--                         gender,
--                         registration_date,
--                         address_id)
-- VALUES ('client 1', 'client_surname 1', '2000-10-11', 'MALE', '2022-09-05', 1),
--        ('client 2', 'client_surname 2', '2001-12-15', 'FEMALE', '2022-09-06', 2);
--
