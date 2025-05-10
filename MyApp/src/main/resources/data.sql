USE myappdatabase;

DELETE
FROM clue;
ALTER TABLE clue
    AUTO_INCREMENT = 1001;

DELETE
FROM category;
ALTER TABLE category
    AUTO_INCREMENT = 1001;

DELETE
FROM game;
ALTER TABLE game
    AUTO_INCREMENT = 1001;

-- Insert data into `game`
INSERT INTO `game` (`name`)
VALUES ('AI Generated Game');

-- Insert data into `category`
INSERT INTO `category` (`name`, `game_id`)
VALUES ('Science', 1001),
       ('History', 1001),
       ('Literature', 1001),
       ('Geography', 1001),
       ('Math', 1001),
       ('Technology', 1001);

-- Insert data into `clue` with 5 clues per category
INSERT INTO `clue` (`question`, `answer`, `value`, `was_picked`, `category_id`)
VALUES
-- Science Category (category_id = 1001)
('What is the chemical symbol for water?', 'H2O', 100, FALSE, 1001),
('What planet is known as the Red Planet?', 'Mars', 200, FALSE, 1001),
('What gas do plants absorb from the atmosphere?', 'Carbon Dioxide', 300, FALSE, 1001),
('Who developed the theory of relativity?', 'Einstein', 400, FALSE, 1001),
('What is the boiling point of water?', '100°C', 500, FALSE, 1001),

-- History Category (category_id = 1002)
('Who was the first President of the United States?', 'George Washington', 100, FALSE, 1002),
('In what year did World War II end?', '1945', 200, FALSE, 1002),
('What was the ancient capital of Egypt?', 'Thebes', 300, FALSE, 1002),
('Who wrote the Declaration of Independence?', 'Thomas Jefferson', 400, FALSE, 1002),
('What was the name of the ship that brought the Pilgrims to America?', 'Mayflower', 500, FALSE, 1002),

-- Literature Category (category_id = 1003)
('Who wrote "Romeo and Juliet"?', 'William Shakespeare', 100, FALSE, 1003),
('What is the first book in the Harry Potter series?', 'The Sorcerer\'s Stone', 200, FALSE, 1003),
('Who is the author of "Moby-Dick"?', 'Herman Melville', 300, FALSE, 1003),
('Which novel begins with "Call me Ishmael"?', 'Moby-Dick', 400, FALSE, 1003),
('Who wrote "Pride and Prejudice"?', 'Jane Austen', 500, FALSE, 1003),

-- Geography Category (category_id = 1004)
('What is the capital of France?', 'Paris', 100, FALSE, 1004),
('Which river is the longest in the world?', 'Nile', 200, FALSE, 1004),
('What country has the largest population?', 'China', 300, FALSE, 1004),
('Which continent is the Sahara Desert located?', 'Africa', 400, FALSE, 1004),
('What is the smallest country in the world?', 'Vatican City', 500, FALSE, 1004),

-- Math Category (category_id = 1005)
('What is the value of Pi to 3 decimal places?', '3.142', 100, FALSE, 1005),
('What is the square root of 144?', '12', 200, FALSE, 1005),
('What is the formula for the area of a circle?', 'πr²', 300, FALSE, 1005),
('What is 7 x 8?', '56', 400, FALSE, 1005),
('What is the name of the longest side of a right triangle?', 'Hypotenuse', 500, FALSE, 1005),

-- Technology Category (category_id = 1006)
('What does CPU stand for?', 'Central Processing Unit', 100, FALSE, 1006),
('Who is known as the father of the computer?', 'Charles Babbage', 200, FALSE, 1006),
('What does HTTP stand for?', 'Hypertext Transfer Protocol', 300, FALSE, 1006),
('What year was the first iPhone released?', '2007', 400, FALSE, 1006),
('What does RAM stand for?', 'Random Access Memory', 500, FALSE, 1006);


