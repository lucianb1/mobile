CREATE TABLE IF NOT EXISTS accounts (
	id INT PRIMARY KEY AUTO_INCREMENT,
	email VARCHAR(100) NOT NULL,
	password BINARY(60) NOT NULL,
	role ENUM("USER", "MEMBER", "MEMBER_ADMIN") NOT NULL,
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

CREATE TABLE IF NOT EXISTS companies (
    id SMALLINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    address VARCHAR(100) NOT NULL,
    member_token VARCHAR(30) NOT NULL,
    coordinates POINT,
    image BLOB,
    timetable VARCHAR(200),
    order_nr SMALLINT NOT NULL DEFAULT 0,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS companies_to_domains (
    id SMALLINT PRIMARY KEY AUTO_INCREMENT,
    company_id SMALLINT NOT NULL,
    domain_id SMALLINT NOT NULL
);

CREATE TABLE IF NOT EXISTS members (
    id SMALLINT PRIMARY KEY AUTO_INCREMENT,
    company_id SMALLINT NOT NULL,
    name varchar(30) NOT NULL,
    image BLOB,
    order_nr SMALLINT NOT NULL DEFAULT 0
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