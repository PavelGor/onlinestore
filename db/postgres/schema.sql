CREATE DATABASE onlinestore
 WITH ENCODING='UTF8'
 OWNER=root

CREATE TABLE product(
  id SERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL,
  priceUah REAL NOT NULL,
  description VARCHAR(500) NOT NULL,
  img_link VARCHAR(145) NOT NULL
  );

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL,
  role VARCHAR(45) NOT NULL
  );


