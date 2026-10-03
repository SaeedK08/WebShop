classDiagram
    direction TB

    %% --- CONTROLLERS (Presentation Layer) ---
    %% Inkluderade baserat på tidigare kontext och standard MVC
    class CartController
    class StaffController
    class ProductController
    class AdminController
    class LoginController

    %% --- SERVICES (Business Logic & Mapping) ---
    class ProductService {
        +getAllProducts() List~ProductInfo~$
        +getProductById(productId) ProductInfo$
        +createProduct(ProductInfo) boolean$
        +updateProduct(ProductInfo) boolean$
        +updateProductStock(productId, stock) boolean$
        +deleteProduct(productId) boolean$
    }
    class OrderService {
        +placeOrder(userId, CartInfo) boolean$
        +getAllOrders() List~OrderInfo~$
        +getOrdersByUserId(userId) List~OrderInfo~$
        +fetchOrderInfos(List~Order~) List~OrderInfo~$
        +packOrder(orderId) boolean$
    }
    class AdminUserService {
        +getAllUsers() List~UserInfo~$
        +changeUserPassword(userId, newPassword) boolean$
        +changeUsername(userId, newUsername) boolean$
        +changeUserRole(userId, newRole) boolean$
        +deleteUser(targetUserId, adminUserId) boolean$
    }

    %% --- DATA ACCESS LAYER (DAOs) ---
    class DBManager {
        -DBManager instance$
        -Connection conn
        -DBManager()
        +getConnection() Connection$
    }
    
    class ProductDB {
        -ProductDB(id, name, description, price, stock, categoryId, categoryName)
        +getAllProducts() List~Product~$
        +getProductById(id) Product$
        +createProduct(Product) boolean$
        +updateProduct(Product) boolean$
        +deleteProduct(productId) boolean$
        +updateProductStock(productId, stock) boolean$
    }
    
    class OrderDB {
        -String BASE_ORDER_QUERY$
        -OrderDB(id, userId, username, orderDate, totalPrice, status, items)
        +placeOrder(Order) boolean$
        +packOrder(orderId) boolean$
        +getAllOrders() List~Order~$
        +getOrdersByUserId(userId) List~Order~$
        -fetchOrders(sql, userId) List~Order~$
    }

    class OrderItemDB {
        -OrderItemDB(id, orderId, productId, productName, quantity, unitPrice)
    }

    class UserDB {
        <<Implied DAO>>
        +getAllUsers() List~User~$
        +validateUser(username, password) User$
    }

    %% --- DOMAIN MODELS ---
    class Product {
        -String name
        -String description
        -int id
        -double price
        -int stock
        -Integer categoryId
        -String categoryName
        #Product(name, description, price, stock, categoryId)
        #Product(id, name, description, price, stock, categoryId, categoryName)
        +getName() String
        +getDescription() String
        +getId() int
        +getPrice() double
        +getStock() int
        +getCategoryId() Integer
        +getCategoryName() String
    }
    
    class Order {
        -int id
        -int userId
        -String username
        -LocalDateTime orderDate
        -double totalPrice
        -String status
        -List~OrderItem~ items
        #Order(id, userId, username, orderDate, totalPrice, status, items)
        #Order(userId, totalPrice, items)
        -createOrder(userId, Cart) Order$
        +addItem(OrderItem)
        +getItems() List~OrderItem~
    }
    
    class OrderItem {
        -int id
        -int orderId
        -int productId
        -String productName
        -int quantity
        -double unitPrice
        #OrderItem(id, orderId, productId, productName, quantity, unitPrice)
        #OrderItem(productId, quantity, unitPrice)
    }

    class User {
        -String username
        -String password
        -int id
        -String role
        #User(username, password, id, role)
        +isAdmin(id) boolean
        +isStaff(id) boolean
    }

    class Cart {
        <<Model>>
        +getItems() List~CartItem~
        +getTotalCartPrice() double
    }

    %% --- DTOs (Data Transfer Objects) ---
    class ProductInfo
    class OrderInfo
    class OrderItemInfo
    class UserInfo
    class CartInfo

    %% --- RELATIONSHIPS ---

    %% Inheritance (DAOs extending Models based on source)
    Product <|-- ProductDB
    Order <|-- OrderDB
    OrderItem <|-- OrderItemDB

    %% Composition / Aggregation
    Order *-- OrderItem : contains
    
    %% Controllers to Services
    CartController --> OrderService : uses
    StaffController --> OrderService : uses
    StaffController --> ProductService : uses
    AdminController --> AdminUserService : uses
    AdminController --> ProductService : uses

    %% Services delegating to Models / DAOs
    ProductService ..> Product : delegates static calls to
    ProductService ..> ProductInfo : creates
    
    OrderService ..> Order : delegates static calls to
    OrderService ..> Cart : maps CartInfo to
    OrderService ..> OrderInfo : creates
    OrderService ..> OrderItemInfo : creates
    
    AdminUserService ..> User : delegates static calls to
    AdminUserService ..> UserInfo : creates

    %% Models delegating to DAOs
    Product ..> ProductDB : statically calls
    Order ..> OrderDB : statically calls
    User ..> UserDB : statically calls

    %% Database connection
    ProductDB ..> DBManager : gets connection
    OrderDB ..> DBManager : gets connection
    UserDB ..> DBManager : gets connection
