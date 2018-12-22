CREATE TABLE category(

	id IDENTITY,
	name VARCHAR(50),
	description VARCHAR(255),
	image_url VARCHAR(50),
	is_active BOOLEAN,
	
	CONSTRAINT pk_category_id PRIMARY KEY (id)
);

INSERT INTO category (name, description, image_url, is_active) VALUES (
'books', 'This is short description of the product', 'PIC1.png', true);
INSERT INTO category (name, description, image_url, is_active) VALUES (
'magazines', 'This is short description of the product', 'PIC2.png', true);
INSERT INTO category (name, description, image_url, is_active) VALUES (
'accessories', 'This is short description of the product', 'PIC3.png', true);

CREATE TABLE user_details (
	id IDENTITY,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	role VARCHAR(50),
	enabled BOOLEAN,
	password VARCHAR(50),
	email VARCHAR(100),
	contact_number VARCHAR(15),
	CONSTRAINT pk_user_id PRIMARY KEY(id)
);

INSERT INTO user_details
(first_name, last_name, role, enabled, password, email, contact_number)
VALUES ('Alex', 'Sky', 'ADMIN', true, 'admin', 'alex@gmail.com', '999999999');

INSERT INTO user_details
(first_name, last_name, role, enabled, password, email, contact_number)
VALUES ('Jonas', 'Jonaitis', 'SUPPLIER', true, '12345', 'jonas@gmail.com', '888899999');

INSERT INTO user_details
(first_name, last_name, role, enabled, password, email, contact_number)
VALUES ('Petras', 'Petraitis', 'SUPPLIER', true, '12345', 'petras@gmail.com', '777799999');

CREATE TABLE product (
	id IDENTITY,
	code VARCHAR(20),
	name VARCHAR(50),
	author VARCHAR(50),
	description VARCHAR(255),
	unit_price DECIMAL(10,2),
	quantity INT,
	is_active BOOLEAN,
	category_id INT,
	supplier_id INT,
	purchases INT DEFAULT 0,
	views INT DEFAULT 0,
	CONSTRAINT pk_product_id PRIMARY KEY (id),
 	CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES category (id),
	CONSTRAINT fk_product_supplier_id FOREIGN KEY (supplier_id) REFERENCES user_details(id),	
);	

INSERT INTO product (code, name, author, description, unit_price, quantity, is_active, category_id, supplier_id, purchases, views)
VALUES ('PRDABC123DEFX', 'iPhone', 'Reed', 'This is best magazine', 180, 5, true, 2, 2, 0, 0 );
INSERT INTO product (code, name, author, description, unit_price, quantity, is_active, category_id, supplier_id, purchases, views)
VALUES ('PRDDEF123DEFX', 'Samsung s7', 'samsung', 'A smart phone by samsung!', 3200, 2, true, 3, 3, 0, 0 );
INSERT INTO product (code, name, author, description, unit_price, quantity, is_active, category_id, supplier_id, purchases, views)
VALUES ('PRDPQR123WGTX', 'Java', 'Horstman', 'This is best book for java developer', 570, 5, true, 1, 2, 0, 0 );
INSERT INTO product (code, name, author, description, unit_price, quantity, is_active, category_id, supplier_id, purchases, views)
VALUES ('PRDMNO123PQRX', 'Python', 'Black', 'This is good book for programmers', 540, 3, true, 1, 2, 0, 0 );
INSERT INTO product (code, name, author, description, unit_price, quantity, is_active, category_id, supplier_id, purchases, views)
VALUES ('PRDABCXYZDEFX', 'Smart Program', 'Hoffer', 'This is affable book for good price', 480, 5, true, 1, 3, 0, 0 );












