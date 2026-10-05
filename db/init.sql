DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS `Order`;
DROP TABLE IF EXISTS OrderItem;

CREATE TABLE Category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);
CREATE TABLE Product (
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     description VARCHAR(200),
     price DECIMAL(10,2) NOT NULL,
     stock INT NOT NULL CHECK (stock>=0),
     categoryId INT NULL,
     FOREIGN KEY (categoryId) REFERENCES Category(id) ON DELETE SET NULL
   );
CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('CUSTOMER', 'STAFF', 'ADMIN') NOT NULL DEFAULT 'CUSTOMER'
);
CREATE TABLE `Order` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    orderDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    totalPrice DECIMAL(10,2) NOT NULL,
    status ENUM('PENDING', 'PACKED') NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE
);
CREATE TABLE OrderItem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    unitPrice decimal(10,2) NOT NULL,
    FOREIGN KEY (orderId) REFERENCES `Order`(id) ON DELETE CASCADE,
    FOREIGN KEY (productId) REFERENCES Product(id)
);
INSERT INTO Product (name, description, price, stock) VALUES ('Coffee', 'Swedish coffee', 49.00, 5), ('Tea', 'English tee with berry taste', 39.00, 10);

INSERT INTO User (username, password, role) VALUES ('saeed', 'password123', 'CUSTOMER'), ('admin', 'admin123', 'ADMIN'),('kasiem', 'kasiem123', 'STAFF');