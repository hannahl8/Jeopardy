USE myappdatabase;

DROP TABLE IF EXISTS clue;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS game;

--
-- Table structure for table `game`
--
CREATE TABLE `game`
(
    `game_id`      INT UNSIGNED AUTO_INCREMENT,
    `name`         VARCHAR(45) NOT NULL,
    `date_created` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`game_id`)
) ENGINE = InnoDB;
ALTER TABLE `game`
    AUTO_INCREMENT = 1001;

--
-- Table structure for table `category`
--
CREATE TABLE `category`
(
    `category_id` INT UNSIGNED AUTO_INCREMENT,
    `name`        VARCHAR(45)  NOT NULL,
    `game_id`     INT UNSIGNED NOT NULL,
    PRIMARY KEY (`category_id`),
    FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE = InnoDB;
ALTER TABLE `category`
    AUTO_INCREMENT = 1001;

--
-- Table structure for table `clue`
--
CREATE TABLE `clue`
(
    `clue_id`     INT UNSIGNED AUTO_INCREMENT,
    `question`    VARCHAR(255) NOT NULL,
    `answer`      VARCHAR(255) NOT NULL,
    `value`       INT     DEFAULT 100,
    `was_picked`  BOOLEAN DEFAULT FALSE,
    `category_id` INT UNSIGNED NOT NULL,
    PRIMARY KEY (`clue_id`),
    FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE = InnoDB;
ALTER TABLE `clue`
    AUTO_INCREMENT = 1001;

--
-- Table structure for table `player`
--
CREATE TABLE `player`
(
    `player_id` INT UNSIGNED AUTO_INCREMENT,
    `name`      VARCHAR(45)  NOT NULL,
    `score`     INT DEFAULT 0,
    `game_id`   INT UNSIGNED NOT NULL,
    PRIMARY KEY (`player_id`),
    FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE = InnoDB;
ALTER TABLE `player`
    AUTO_INCREMENT = 1001;