create table shops(
id_shop int(11) PRIMARY KEY auto_increment,
name varchar(255) not null
);

create table products (
id_product int(11) PRIMARY KEY auto_increment,
product_name VARCHAR (255) not null,
product_price float(10,2) not null,
id_shop int,
constraint fk_id_shop foreign key (id_shop) references shops(id_shop) ON DELETE CASCADE
);

create table clients(
id_client int (11) PRIMARY KEY auto_increment,
name_client varchar(255) not null,
last_name_client varchar(255) not null,
email_client varchar(255) not null
);

create table purchases(
id_purchase int (11) PRIMARY KEY auto_increment,
id_client int,
id_product int,
constraint fk_id_client foreign key (id_client) references clients(id_client) ON DELETE CASCADE,
constraint fk_id_product foreign key (id_product) references products(id_product) ON DELETE CASCADE
);

ALTER TABLE products
ADD COLUMN stock int (11) not null;


select * FROM clients;
UPDATE clients SET name_client="pepito", last_name_client="perez", email_client ="pepito@gmail.com" WHERE id_client=5;

ALTER TABLE shops
ADD COLUMN ubication varchar(255) not null;


SELECT * FROM shops;
INSERT INTO shops VALUES(id_shop,"ADIDAS","1 PISO");
INSERT INTO shops VALUES(id_shop,"BRANCHOS","1 PISO");
INSERT INTO shops VALUES(id_shop,"NEWERA","1 PISO");

SELECT * FROM shops;

SELECT * FROM products;
SELECT * FROM products
INNER JOIN shops 
ON  shops.id_shop=products.id_shop;

		
        
SELECT * FROM reservations 
INNER JOIN passengers
ON passengers.id_passenger = reservations.id_passenger;

INSERT INTO products (id_product, product_name, product_price, id_shop,stock) VALUES (1,"zapato feo",10000,1,10);

UPDATE products SET product_name="Zapato Feo", product_price=20000,id_shop=1,stock=15 WHERE id_product=1;

