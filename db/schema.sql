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

CREATE TABLE `user` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `session` (
  `id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `token` VARCHAR(255) NOT NULL,
  `expired_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `cart` (
  `session_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `product_qty` INT NOT NULL);


