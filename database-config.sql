-- database-config.sql
USE mysql;

CREATE USER 'yechan'@'%' IDENTIFIED BY '0joker@';
GRANT ALL PRIVILEGES ON auth_db.* TO 'yechan'@'%' IDENTIFIED BY '0joker@';
FLUSH PRIVILEGES;

SHOW GRANTS FOR 'yechan'@'%';

USE auth_db;