drop database mobile;
create database mobile;
use mobile;


CREATE TABLE IF NOT EXISTS accounts (
	id INT PRIMARY KEY AUTO_INCREMENT,
	email VARCHAR(100) UNIQUE NOT NULL,
	password BINARY(60) NOT NULL,
	role ENUM("USER", "MEMBER", "MEMBER_ADMIN", "ADMIN") NOT NULL,
	phone VARCHAR(20) NOT NULL,
	name VARCHAR(50) NOT NULL,
	created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS remember_me_tokens (
	id INT PRIMARY KEY AUTO_INCREMENT,
	account_id INT NOT NULL,
	token VARCHAR(50) NOT NULL,
	created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS company_domains (
    id SMALLINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    image BLOB,
    order_nr SMALLINT NOT NULL DEFAULT 0
);


INSERT INTO company_domains(name, order_nr)
SELECT * FROM (
	SELECT name, order_nr FROM company_domains UNION ALL
	SELECT 'Frizerii', 1  FROM DUAL UNION ALL
	SELECT 'Machiaj',  2  FROM DUAL UNION ALL
	SELECT 'Cosmetica', 3 FROM DUAL UNION ALL
	SELECT 'Masaj', 4     FROM DUAL
) AS t
WHERE (SELECT COUNT(id) FROM company_domains) = 0;


CREATE TABLE IF NOT EXISTS companies (
    id SMALLINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    address VARCHAR(100) NOT NULL,
    coordinates POINT,
    order_nr SMALLINT NOT NULL DEFAULT 0,
    member_token VARCHAR(20) NOT NULL,
    member_admin_token VARCHAR(20) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--CREATE TABLE IF NOT EXISTS member_tokens (
--    id SMALLINT PRIMARY KEY AUTO_INCREMENT,
--    company_id SMALLINT NOT NULL,
--    token VARCHAR(20) NOT NULL,
--    is_admin BIT NOT NULL
--);

CREATE TABLE IF NOT EXISTS companies_to_domains (
    id SMALLINT PRIMARY KEY AUTO_INCREMENT,
    company_id SMALLINT NOT NULL,
    domain_id SMALLINT NOT NULL
);

CREATE TABLE IF NOT EXISTS members (
    id SMALLINT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    company_id SMALLINT NOT NULL,
    name varchar(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    order_nr SMALLINT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS member_services (
    id TINYINT PRIMARY KEY AUTO_INCREMENT,
    member_id SMALLINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    duration TINYINT NOT NULL, -- in quarters
    order_nr TINYINT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS appointments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    member_id SMALLINT NOT NULL,
    member_service SMALLINT NOT NULL,
    date DATE NOT NULL,
    hour SMALLINT NOT NULL, -- in quarters
    status VARCHAR(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS member_tokens (
    id SMALLINT PRIMARY KEY AUTO_INCREMENT,
    company_id SMALLINT NOT NULL,
    token VARCHAR(30) NOT NULL,
    is_admin bit NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS member_default_timetables (
    id SMALLINT PRIMARY KEY AUTO_INCREMENT,
    member_id SMALLINT NOT NULL,
    timetable BLOB
);

CREATE TABLE IF NOT EXISTS member_day_timetables (
    id INT PRIMARY KEY AUTO_INCREMENT,
    member_id SMALLINT NOT NULL,
    date DATE NOT NULL,
    timetable BINARY(192) NOT NULL, -- 4 * 24 * 2 bytes
    UNIQUE KEY unique_day_index (member_id, date)
);

/**
role(USER, MEMBER, MEMBER_ADMIN)
accounts(id, email, password, type, gender, phone, name, is_facebook)
remember_me_tokens(id, account_id, token, creation_date)
password_change_tokens(id, account_id, new_password, token, creation_date)
company(id, name, address, image, lat, long, member_code)
domains(id, name)
company_domains(id, company_id, domain_id)
members(id, account_id, company_id, image)
member_domains(id, member_id, domain_id)
member_reviews(id, member_id, account_id, title, message, stars)
member_timetables(id, member_id)
member_services(id, member_id, domain_id, name, duration, price??)
appointments(id, account_id, member_id, service_id)
*/