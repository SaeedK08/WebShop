DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS User;

CREATE TABLE Product (
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     description VARCHAR(200),
     price DECIMAL(10,2) NOT NULL,
     stock INT NOT NULL
   );
CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('user', 'staff', 'admin') NOT NULL DEFAULT 'user'
);
INSERT INTO Product (name, description, price, stock) VALUES ('Coffee', 'Swedish coffee', 49.00, 4), ('Tea', 'English tee with berry taste', 39.00, 10);

INSERT INTO User (username, password, role) VALUES ('saeed', 'password123', 'user'), ('admin', 'admin123', 'admin'),('kasiem', 'kasiem123', 'staff');