CREATE DATABASE `onlinestore` DEFAULT CHARACTER SET utf8 ;

use `onlinestore`;

CREATE TABLE `group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` FLOAT NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  `group_id` INT NOT NULL,
  `img_link` VARCHAR(145) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci',
  PRIMARY KEY (`id`));


INSERT INTO `product` (`name`, `price`, `description`, `group_id`, `img_link`) VALUES
('Яблочный', 10.5, 'Сок такой вот Яблочный', 1, 'хз_куда/');

INSERT INTO `product` (`name`, `price`, `description`, `group_id`, `img_link`) VALUES
('Знамя газ', 8.33, 'Минеральная вода', 3, 'тоже не туда');