-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "paproject" database.
-------------------------------------------------------------------------------

INSERT INTO User (userName, firstName, lastName, email, role, password) VALUES
    ('usuario1', 'Ash', 'Ketchum', 'usuario1@pa-gei.udc.es','0', '$2a$10$zZnDjiu51rH1SeuL6HqH9ORE1ZHRaJGLBzNi.vjYfhLJ5ZVYF0RMC');

INSERT INTO User (userName, firstName, lastName, email, role, password) VALUES
    ('usuario2', 'Axel', 'Blaze', 'usuario2@pa-gei.udc.es','0', '$2a$10$zZnDjiu51rH1SeuL6HqH9ORE1ZHRaJGLBzNi.vjYfhLJ5ZVYF0RMC');

INSERT INTO User (userName, firstName, lastName, email, role, password) VALUES
    ('usuario3', 'Nezuko', 'Kamado', 'usuario3@pa-gei.udc.es','0', '$2a$10$zZnDjiu51rH1SeuL6HqH9ORE1ZHRaJGLBzNi.vjYfhLJ5ZVYF0RMC');

INSERT INTO User(userName, firstName, lastName, email, role, password) VALUES
('test','testName','testLastName','test@pa-gei.udc.es','0','$2y$10$wB9YW/NXhKa.xBS1FQSzdONfxt0amVzes3ijcWqin43qHhP9Onxki');

INSERT INTO Category (name) VALUES ('Portatiles');
INSERT INTO Category (name) VALUES ('Pantallas');

INSERT INTO Product (name, description, startingPrice, actualPrice, startingDate, finishingDate, deliveryInformation, userId, categoryId) VALUES
('Portatil 1', 'Portatil 1 ASUS', 10.0,10.0, '2022-01-01T17:00:00', '2022-01-01T17:30:00', 'entrega en una semana', 1, 1);

INSERT INTO Product (name, description, startingPrice, actualPrice, startingDate, finishingDate, deliveryInformation, userId, categoryId) VALUES
('Portatil 2', 'Portatil 2 ASUS', 10.0, 10.0,'2022-01-01T17:00:00', '2022-09-01T00:00:00', 'entrega en una semana', 1, 1);

INSERT INTO Product (name, description, startingPrice, actualPrice, startingDate, finishingDate, deliveryInformation, userId, categoryId) VALUES
('Pantalla 1', 'Pantalla 1 LG', 10.0, 10.0, '2022-01-01T17:30:00', '2022-10-01T00:00:00', 'entrega en una semana', 1, 2);

INSERT INTO Product (name, description, startingPrice, actualPrice, startingDate, finishingDate, deliveryInformation, userId, categoryId) VALUES
('Pantalla 2', 'Pantalla 2 LG', 10.0, 10.0, '2022-01-01T17:30:00', '2022-11-01T00:00:00', 'entrega en una semana', 1, 2);