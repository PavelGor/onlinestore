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
  `imglink` VARCHAR(145) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL,
  PRIMARY KEY (`productid`));

