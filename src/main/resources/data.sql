-- Вставка данных в таблицу programmers
INSERT INTO programmers (name, email, experience_years, education, company) VALUES
                                                                                ('John Doe', 'john.doe@example.com', 5, 'MIT', 'Google'),
                                                                                ('Alice Smith', 'alice.smith@example.com', 3, 'Harvard', 'Facebook'),
                                                                                ('Bob Johnson', 'bob.johnson@example.com', 7, 'Stanford', 'Amazon'),
                                                                                ('Charlie Brown', 'charlie.brown@example.com', 2, 'Oxford', 'Microsoft'),
                                                                                ('David Wilson', 'david.wilson@example.com', 10, 'Cambridge', 'Apple'),
                                                                                ('Eva Green', 'eva.green@example.com', 6, 'UCLA', 'IBM'),
                                                                                ('Frank Harris', 'frank.harris@example.com', 4, 'Princeton', 'Netflix'),
                                                                                ('Grace Lee', 'grace.lee@example.com', 5, 'Harvard', 'Twitter'),
                                                                                ('Helen White', 'helen.white@example.com', 8, 'MIT', 'Uber'),
                                                                                ('Ivy Clark', 'ivy.clark@example.com', 3, 'Stanford', 'Spotify'),
                                                                                ('James Walker', 'james.walker@example.com', 9, 'Caltech', 'Salesforce'),
                                                                                ('Kelly King', 'kelly.king@example.com', 6, 'Cambridge', 'Google'),
                                                                                ('Liam Turner', 'liam.turner@example.com', 7, 'UCLA', 'Facebook'),
                                                                                ('Megan Scott', 'megan.scott@example.com', 4, 'Oxford', 'Microsoft'),
                                                                                ('Nina Adams', 'nina.adams@example.com', 5, 'MIT', 'Google'),
                                                                                ('Oscar Young', 'oscar.young@example.com', 3, 'Harvard', 'Facebook'),
                                                                                ('Paul Harris', 'paul.harris@example.com', 6, 'Stanford', 'Amazon'),
                                                                                ('Quinn Lee', 'quinn.lee@example.com', 7, 'Oxford', 'Google'),
                                                                                ('Rachel King', 'rachel.king@example.com', 4, 'MIT', 'Facebook'),
                                                                                ('Steve Roberts', 'steve.roberts@example.com', 5, 'UCLA', 'Amazon'),
                                                                                ('Tina Scott', 'tina.scott@example.com', 3, 'Princeton', 'IBM'),
                                                                                ('Uma Walker', 'uma.walker@example.com', 8, 'Stanford', 'Salesforce'),
                                                                                ('Victor Evans', 'victor.evans@example.com', 6, 'Caltech', 'Twitter'),
                                                                                ('Wendy Turner', 'wendy.turner@example.com', 4, 'Cambridge', 'Uber'),
                                                                                ('Xander Green', 'xander.green@example.com', 5, 'Harvard', 'Netflix'),
                                                                                ('Yvonne Clark', 'yvonne.clark@example.com', 7, 'Oxford', 'Spotify'),
                                                                                ('Zack White', 'zack.white@example.com', 6, 'Princeton', 'Uber');

-- Вставка данных в таблицу technologies
INSERT INTO technologies (name, category, rating, is_required) VALUES
                                                                   ('Java', 'Backend', 0.9),
                                                                   ('Python', 'Data Science', 0.85),
                                                                   ('JavaScript', 'Web Development', 0.8),
                                                                   ('C++', 'Systems Programming', 0.95),
                                                                   ('SQL', 'Database', 1.0),
                                                                   ('Ruby', 'Web Development', 0.75),
                                                                   ('Go', 'Cloud', 0.8),
                                                                   ('Kotlin', 'Mobile', 0.7),
                                                                   ('Swift', 'Mobile', 0.85),
                                                                   ('PHP', 'Web Development', 0.65);

-- Вставка данных в таблицу skill_levels
INSERT INTO skill_levels (programmer_id, technology_id, level) VALUES
                                                                   (1, 1, 0.8), (1, 2, 0.7), (1, 3, 0.6), (1, 4, 0.9), (1, 5, 1.0),
                                                                   (2, 1, 0.6), (2, 2, 0.9), (2, 3, 0.75), (2, 4, 0.85), (2, 5, 0.95),
                                                                   (3, 1, 1.0), (3, 2, 0.7), (3, 3, 0.65), (3, 4, 0.85), (3, 5, 0.8),
                                                                   (4, 1, 0.75), (4, 2, 0.85), (4, 3, 0.8), (4, 4, 0.9), (4, 5, 0.8),
                                                                   (5, 1, 0.9), (5, 2, 0.75), (5, 3, 0.65), (5, 4, 1.0), (5, 5, 0.7),
                                                                   (6, 1, 0.8), (6, 2, 0.9), (6, 3, 0.7), (6, 4, 0.6), (6, 5, 1.0),
                                                                   (7, 1, 0.7), (7, 2, 0.75), (7, 3, 0.8), (7, 4, 0.9), (7, 5, 0.8),
                                                                   (8, 1, 0.75), (8, 2, 0.65), (8, 3, 0.7), (8, 4, 0.8), (8, 5, 0.85),
                                                                   (9, 1, 0.9), (9, 2, 0.8), (9, 3, 0.75), (9, 4, 0.85), (9, 5, 0.8),
                                                                   (10, 1, 0.8), (10, 2, 0.75), (10, 3, 0.6), (10, 4, 0.85), (10, 5, 0.9),
                                                                   (11, 1, 0.6), (11, 2, 0.8), (11, 3, 0.9), (11, 4, 0.7), (11, 5, 0.75),
                                                                   (12, 1, 0.7), (12, 2, 0.85), (12, 3, 0.75), (12, 4, 0.9), (12, 5, 1.0),
                                                                   (13, 1, 0.9), (13, 2, 0.7), (13, 3, 0.65), (13, 4, 0.8), (13, 5, 0.75),
                                                                   (14, 1, 1.0), (14, 2, 0.8), (14, 3, 0.75), (14, 4, 0.7), (14, 5, 0.85),
                                                                   (15, 1, 0.8), (15, 2, 0.75), (15, 3, 0.9), (15, 4, 0.7), (15, 5, 0.8),
                                                                   (16, 1, 0.7), (16, 2, 0.85), (16, 3, 0.8), (16, 4, 0.9), (16, 5, 1.0),
                                                                   (17, 1, 0.9), (17, 2, 0.75), (17, 3, 0.8), (17, 4, 0.7), (17, 5, 0.85),
                                                                   (18, 1, 0.8), (18, 2, 0.9), (18, 3, 0.7), (18, 4, 0.75), (18, 5, 0.95),
                                                                   (19, 1, 0.85), (19, 2, 0.7), (19, 3, 0.6), (19, 4, 0.75), (19, 5, 0.8),
                                                                   (20, 1, 0.8), (20, 2, 0.75), (20, 3, 0.85), (20, 4, 0.7), (20, 5, 0.8),
                                                                   (21, 1, 0.75), (21, 2, 0.8), (21, 3, 0.85), (21, 4, 0.7), (21, 5, 0.9),
                                                                   (22, 1, 0.8), (22, 2, 0.75), (22, 3, 0.7), (22, 4, 0.9), (22, 5, 0.8),
                                                                   (23, 1, 0.85), (23, 2, 0.7), (23, 3, 0.9), (23, 4, 0.75), (23, 5, 0.8),
                                                                   (24, 1, 0.8), (24, 2, 0.75), (24, 3, 0.7), (24, 4, 0.8), (24, 5, 0.9),
                                                                   (25, 1, 0.9), (25, 2, 0.7), (25, 3, 0.8), (25, 4, 0.75), (25, 5, 0.85),
                                                                   (26, 1, 0.85), (26, 2, 0.8), (26, 3, 0.7), (26, 4, 0.9), (26, 5, 0.75),
                                                                   (27, 1, 0.9), (27, 2, 0.8), (27, 3, 0.75), (27, 4, 0.7), (27, 5, 0.8);



-- Вставка данных в таблицу projects
INSERT INTO projects (project_name, description, start_date, end_date) VALUES
                                                                           ('Project A', 'Description of Project A', '2025-01-01', '2025-12-31'),
                                                                           ('Project B', 'Description of Project B', '2025-02-01', '2025-11-30'),
                                                                           ('Project C', 'Description of Project C', '2025-03-01', '2025-10-31'),
                                                                           ('Project D', 'Description of Project D', '2025-04-01', '2025-09-30'),
                                                                           ('Project E', 'Description of Project E', '2025-05-01', '2025-08-31');


-- Вставка данных в таблицу groups
INSERT INTO groups (group_name, project_id) VALUES
                                                ('Group 1', 1),
                                                ('Group 2', 1),
                                                ('Group 3', 2),
                                                ('Group 4', 2),
                                                ('Group 5', 3),
                                                ('Group 6', 3),
                                                ('Group 7', 4),
                                                ('Group 8', 4),
                                                ('Group 9', 5),
                                                ('Group 10', 5);

-- Вставка данных в таблицу group_members (для примера: 3 программиста в каждой группе)
INSERT INTO group_members (group_id, programmer_id) VALUES
                                                        (1, 1), (1, 2), (1, 3),
                                                        (2, 4), (2, 5), (2, 6),
                                                        (3, 7), (3, 8), (3, 9),
                                                        (4, 10), (4, 11), (4, 12),
                                                        (5, 13), (5, 14), (5, 15),
                                                        (6, 16), (6, 17), (6, 18),
                                                        (7, 19), (7, 20), (7, 21),
                                                        (8, 22), (8, 23), (8, 24),
                                                        (9, 25), (9, 26), (9, 27),
                                                        (10, 25), (10, 19), (10, 12);

-- Вставка данных в таблицу thresholds
INSERT INTO thresholds (project_id, technology_id, min_level, min_group_level) VALUES
                                                                                   (1, 1, 0.75, 0.8),
                                                                                   (1, 2, 0.7, 0.75),
                                                                                   (1, 3, 0.6, 0.7),
                                                                                   (1, 4, 0.8, 0.85),
                                                                                   (1, 5, 0.65, 0.75),
                                                                                   (2, 1, 0.8, 0.85),
                                                                                   (2, 2, 0.75, 0.8),
                                                                                   (2, 3, 0.7, 0.75),
                                                                                   (2, 4, 0.85, 0.9),
                                                                                   (2, 5, 0.7, 0.8),
                                                                                   (3, 1, 0.7, 0.75),
                                                                                   (3, 2, 0.75, 0.8),
                                                                                   (3, 3, 0.65, 0.7),
                                                                                   (3, 4, 0.9, 0.95),
                                                                                   (3, 5, 0.8, 0.85),
                                                                                   (4, 1, 0.8, 0.85),
                                                                                   (4, 2, 0.7, 0.75),
                                                                                   (4, 3, 0.6, 0.7),
                                                                                   (4, 4, 0.85, 0.9),
                                                                                   (4, 5, 0.75, 0.8),
                                                                                   (5, 1, 0.75, 0.8),
                                                                                   (5, 2, 0.7, 0.75),
                                                                                   (5, 3, 0.65, 0.7),
                                                                                   (5, 4, 0.8, 0.85),
                                                                                   (5, 5, 0.7, 0.75);
