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
	password VARCHAR(60),
	email VARCHAR(100),
	contact_number VARCHAR(15),
	CONSTRAINT pk_user_id PRIMARY KEY(id)
);

INSERT INTO user_details
(first_name, last_name, role, enabled, password, email, contact_number)
VALUES ('Alex', 'Sky', 'USER', true, '$2y$12$u1svJu2B7c1eRzHju1tWke71ydHzx.G/U8QQBKWeYQkb8IhcCVn.C', 'alex@gmail.com', '999999999');

INSERT INTO user_details
(first_name, last_name, role, enabled, password, email, contact_number)
VALUES ('Jonas', 'Jonaitis', 'SUPPLIER', true, '$2y$12$MflMRdRpwrmNhPejveR92eU9fbr12SWWf3ghzxFbm44BVHQXVd1QS', 'jonas@gmail.com', '888899999');

INSERT INTO user_details
(first_name, last_name, role, enabled, password, email, contact_number)
VALUES ('Petras', 'Petraitis', 'SUPPLIER', true, '$2y$12$ZhZg4EFKDksCTbem81b52elTNTdiPqrT6R1Z21PXy2jd9ZXp8yy2.', 'petras@gmail.com', '777799999');

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


CREATE TABLE cart_line (
	id IDENTITY,
	cart_id int,
	total DECIMAL(10,2),
	product_id int,
	product_count int,
	buying_price DECIMAL(10,2),
	is_available boolean,
	CONSTRAINT fk_cartline_cart_id FOREIGN KEY (cart_id ) REFERENCES cart (id),
	CONSTRAINT fk_cartline_product_id FOREIGN KEY (product_id ) REFERENCES product (id),
	CONSTRAINT pk_cartline_id PRIMARY KEY (id)
);


-- the order detail table to store the order

CREATE TABLE order_detail (
	id IDENTITY,
	user_id int,
	order_total DECIMAL(10,2),
	order_count int,
	shipping_id int,
	billing_id int,
	order_date date,
	CONSTRAINT fk_order_detail_user_id FOREIGN KEY (user_id) REFERENCES user_detail (id),
	CONSTRAINT fk_order_detail_shipping_id FOREIGN KEY (shipping_id) REFERENCES address (id),
	CONSTRAINT fk_order_detail_billing_id FOREIGN KEY (billing_id) REFERENCES address (id),
	CONSTRAINT pk_order_detail_id PRIMARY KEY (id)
);

-- the order item table to store order items

CREATE TABLE order_item (
	id IDENTITY,
	order_id int,
	total DECIMAL(10,2),
	product_id int,
	product_count int,
	buying_price DECIMAL(10,2),
	CONSTRAINT fk_order_item_product_id FOREIGN KEY (product_id) REFERENCES product (id),
	CONSTRAINT fk_order_item_order_id FOREIGN KEY (order_id) REFERENCES order_detail (id),
	CONSTRAINT pk_order_item_id PRIMARY KEY (id)
);










