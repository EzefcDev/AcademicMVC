CREATE TABLE students(id INT NOT NULL AUTO_INCREMENT, name VARCHAR(128) NOT NULL, lastname VARCHAR(128) NOT NULL, dni INT NOT NULL, student_status int, student_career int, delete_student date default null, PRIMARY KEY(id));

CREATE TABLE users(user_id INT NOT NULL AUTO_INCREMENT, user_name VARCHAR(128) NOT NULL, password VARCHAR(128) NOT NULL,email VARCHAR(128) NOT NULL,locked TINYINT, disabled TINYINT, PRIMARY KEY(user_id));

CREATE TABLE users_roles(role_id INT NOT NULL AUTO_INCREMENT, user_id INT NOT NULL,role VARCHAR(50) NOT NULL, creation_date DATE NOT NULL,PRIMARY KEY(role_id) ,FOREIGN KEY (user_id) REFERENCES users(user_id));

CREATE TABLE types(value_id INT NOT NULL, meaning VARCHAR(128) NOT NULL, type VARCHAR(128) NOT NULL, PRIMARY KEY(value_id));

INSERT INTO students(name, lastname, dni, student_status, student_career, delete_student)
VALUES  ('Alejando', 'Maldonado', 30345789, 1, 1, null),
        ('Ramiro', 'Juarez', 32345781, 2, 1, null),
        ('Vanesa', 'Redondo', 23347089, 3, 2, null),
        ('Mariana', 'Crisantemo', 36245709, 1, 3, null),
        ('Alejando', 'Maldonado', 30345789, 1, 1, null),
        ('Ramiro', 'Juarez', 32345781, 2, 1, null),
        ('Vanesa', 'Redondo', 23347089, 3, 2, null),
        ('Mariana', 'Crisantemo', 36245709, 1, 3, null),
        ('Alejando', 'Maldonado', 30345789, 1, 1, null),
        ('Ramiro', 'Juarez', 32345781, 2, 1, null),
        ('Vanesa', 'Redondo', 23347089, 3, 2, null),
        ('Mariana', 'Cinceló', 36245709, 1, 3, null),
        ('Alejando', 'Maldonado', 30345789, 1, 1, null),
        ('Ramiro', 'Juarez', 32345781, 2, 1, null),
        ('Vanesa', 'Redondo', 23347089, 3, 2, null),
        ('Mariana', 'Cinceló', 36245709, 1, 3, null),
        ('Alejando', 'Maldonado', 30345789, 1, 1, null),
        ('Ramiro', 'Juarez', 32345781, 2, 1, null),
        ('Vanesa', 'Redondo', 23347089, 3, 2, null),
        ('Mariana', 'Crisantemo', 36245709, 1, 3, null),
        ('Alejando', 'Maldonado', 30345789, 1, 1, null),
        ('Ramiro', 'Juarez', 32345781, 2, 1, null),
        ('Vanesa', 'Redondo', 23347089, 3, 2, null),
        ('Mariana', 'Crisantemo', 36245709, 1, 3, null),
        ('Alejando', 'Maldonado', 30345789, 1, 1, null);

INSERT INTO types(value_id, meaning, type)
VALUES  (1, 'Cursando', 'student_status' ),
        (2, 'Titulado', 'student_status' ),
        (3, 'Baja', 'student_status' );

INSERT INTO users(user_id, user_name, password,email,locked,disabled)
VALUES  (1, 'Admin', '$2y$10$IFMKEBHH8MlKFY4/r/h.iOJrrPrURzVCW8o24LzHQgSyJo58DttEK','admin@email.com',0,0 ),
        (2, 'Customer', '$2y$10$J4c8DaZQdt.962.Ll/HM.upYv7m2FHmcslvwjsNuVpJl9WVMdJzEW','customer@email.com',0,0 );

INSERT INTO users_roles (role_id ,user_id, role, creation_date)
VALUES (1,  1, 'ADMIN', CURRENT_DATE),
       (2,  2, 'USER', CURRENT_DATE);
