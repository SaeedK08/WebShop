````mermaid
classDiagram
    direction TB

    %% CONTROLLERS
    class CartController
    class StaffController
    class ProductController
    class AdminController
    class LoginController

    %% SERVICES
    class ProductService {
        +getAllProducts() List
        +getProductById(productId) ProductInfo
        +createProduct(ProductInfo) boolean
        +updateProductStock(productId, stock) boolean
    }
    class OrderService {
        +placeOrder(userId, CartInfo) boolean
        +getAllOrders() List
        +getOrdersByUserId(userId) List
        +packOrder(orderId) boolean
    }
    class AdminUserService {
        +getAllUsers() List
        +changeUserRole(userId, newRole) boolean
    }

    %% DAOs & DB
    class DBManager {
        +getConnection() Connection
    }
    class ProductDB {
        +getAllProducts() List
        +updateProductStock(productId, stock) boolean
    }
    class OrderDB {
        +placeOrder(Order) boolean
        +packOrder(orderId) boolean
    }
    class UserDB {
        +validateUser(username, password) User
    }

    %% MODELS
    class Product {
        -int id
        -String name
        -int stock
    }
    class Order {
        -int id
        -String status
        -List items
    }
    class OrderItem {
        -int productId
        -int quantity
    }
    class User {
        -String username
        -String role
    }

    %% RELATIONSHIPS
    Product <|-- ProductDB
    Order <|-- OrderDB
    Order *-- OrderItem
    
    CartController --> OrderService
    StaffController --> OrderService
    StaffController --> ProductService
    AdminController --> AdminUserService
    
    ProductService ..> Product
    OrderService ..> Order
    AdminUserService ..> User
    
    ProductDB ..> DBManager
    OrderDB ..> DBManager
    UserDB ..> DBManager`