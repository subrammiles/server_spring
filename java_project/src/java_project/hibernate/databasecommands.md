


CREATE DATABASE myDataBase;
use myDataBase

CREATE TABLE  users(
user_id int NOT NULL AUTO_INCREMENT,
username varchar(25) NOT NULL,
password varchar(30) ,
email varchar(31) ,
phone varchar (25),
account_status int NOT NULL,
gender VARCHAR(9),
creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (user_id)
);


create user 'testuser' identified by '*********'; 