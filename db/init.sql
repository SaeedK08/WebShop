DROP TABLE IF EXISTS Product;

CREATE TABLE Product (
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     description VARCHAR(200),
     price DECIMAL(10,2) NOT NULL,
     stock INT NOT NULL
   );
INSERT INTO Product (name, description, price, stock) VALUES ('Coffee', 'Swedish coffee', 49.00, 4), ('Tea', 'English tee with berry taste', 39.00, 10);