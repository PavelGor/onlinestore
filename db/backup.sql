CREATE SCHEMA `onlinestore` DEFAULT CHARACTER SET utf8 ;

use `onlinestore`

CREATE TABLE `onlinestore`.`group` (
  `groupid` INT NOT NULL AUTO_INCREMENT,
  `groupname` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,
  PRIMARY KEY (`groupid`));

CREATE TABLE `onlinestore`.`product` (
  `productid` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` FLOAT NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  `groupid` INT NOT NULL,
  `imglink` VARCHAR(145) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci',
  PRIMARY KEY (`productid`));



ALTER TABLE `product`
CHANGE COLUMN `productid` `id` INT(11) NOT NULL AUTO_INCREMENT ,
CHANGE COLUMN `groupid` `group_id` INT(11) NOT NULL ,
CHANGE COLUMN `imglink` `img_link` VARCHAR(145) ;

ALTER TABLE `onlinestore`.`group`
CHANGE COLUMN `groupid` `id` INT(11) NOT NULL AUTO_INCREMENT ,
CHANGE COLUMN `groupname` `group_name` VARCHAR(45) NULL DEFAULT NULL ;

INSERT INTO `product` (`name`, `price`, `description`, `group_id`, `img_link`) VALUES
('Яблочный', 10.5, 'Сок такой вот Яблочный', 1, 'хз_куда/');

INSERT INTO `product` (`name`, `price`, `description`, `group_id`, `img_link`) VALUES
('Знамя газ', 8.33, 'Минеральная вода', 3, 'тоже не туда');