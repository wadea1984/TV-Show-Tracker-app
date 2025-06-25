use tv_show_tracker;
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE show_tracker;
TRUNCATE TABLE tv_shows;
TRUNCATE TABLE users;

SET FOREIGN_KEY_CHECKS = 1;
-- Insert 5 users
INSERT INTO users (username, email, password) VALUES
('alex1984', 'alex@example.com', 'password123'),
('bravoMike', 'bravo@example.com', 'hunter2secure'),
('charlieFox', 'charlie@example.com', 'mypassword8'),
('deltaQueen', 'delta@example.com', 'adminpass123'),
('echoZulu', 'echo@example.com', 'securekeypass');

-- Insert 15 TV shows
INSERT INTO tv_shows (name, episodes, description, genre) VALUES
('Breaking Bad', 62, 'A chemistry teacher turned meth producer.', 'Crime Drama'),
('Game of Thrones', 73, 'Noble families fight for control of the Iron Throne.', 'Fantasy'),
('Stranger Things', 34, 'Kids face supernatural threats in a small town.', 'Sci-Fi Horror'),
('The Office', 201, 'Mockumentary about a paper company.', 'Comedy'),
('Friends', 236, 'Six friends navigate life and love in NYC.', 'Sitcom'),
('The Witcher', 24, 'Monster hunter Geralt battles fate and beasts.', 'Fantasy'),
('Better Call Saul', 63, 'The journey of Jimmy McGill to Saul Goodman.', 'Legal Drama'),
('The Mandalorian', 24, 'A lone bounty hunter in the Star Wars universe.', 'Sci-Fi'),
('The Crown', 60, 'The reign of Queen Elizabeth II.', 'Historical Drama'),
('The Boys', 32, 'Vigilantes confront corrupt superheroes.', 'Superhero Drama'),
('Dark', 26, 'Time travel and family secrets in a German town.', 'Sci-Fi Thriller'),
('Money Heist', 41, 'A criminal mastermind leads heists on Spain’s mint.', 'Heist Thriller'),
('Loki', 12, 'The god of mischief causes multiverse chaos.', 'Superhero Fantasy'),
('Ozark', 44, 'A family launders money for a drug cartel.', 'Crime Thriller'),
('Peaky Blinders', 36, 'A gangster family in post-WWI Birmingham.', 'Historical Crime Drama');


-- Users tracking 2 shows each
INSERT INTO show_tracker (show_id, user_id, episodes_watched, rating, status) VALUES
(1, 1, 10, 5, 'Currently Watching'),
(2, 1, 62, 5, 'Finished'),
(3, 2, 20, 4, 'Currently Watching'),
(4, 2, 0, NULL, 'Plan to Watch'),
(5, 3, 236, 5, 'Finished'),
(6, 3, 12, 3, 'Currently Watching'),
(7, 4, 63, 5, 'Finished'),
(8, 4, 5, NULL, 'Plan to Watch'),
(9, 5, 25, 4, 'Currently Watching'),
(10, 5, 32, 4, 'Finished');

select * from tv_shows;