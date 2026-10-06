## Requirements

To build and run this project locally, you need the following environment:

* **Java Development Kit (JDK):** Version 11 or higher.
* **Web Server / Servlet Container:** Apache Tomcat 9. *(Note: The project uses the `javax.servlet` package, which requires Tomcat 9 or older. It will not work out-of-the-box on Tomcat 10+).*
* **Dependency Management:** Maven (automatically handles dependencies like the MySQL Connector/J JDBC driver via `pom.xml`).
* **Database Environment:** Docker and Docker Compose.
* **IDE:** IntelliJ IDEA (Ultimate highly recommended for built-in Tomcat support) or equivalent.
* **IDE extensions/plugins:** SmartTomcat in InteliJ, surely similiar extensions can be found in other IDEs.


<br></br>
## Setup & Usage

### Initialize the Database
The database environment is fully containerized. To start the MySQL database, navigate to the project root in your terminal and run:

```bash
$ docker compose up -d
```

<br></br>
## Class diagram

Architecture overview of the webshop application, showing the three main layers:

- **Controllers** – handle incoming requests (`CartController`, `StaffController`, `ProductController`, `AdminController`, `LoginController`)
- **Services** – business logic (`OrderService`, `ProductService`, `AdminUserService`)
- **DB layer** – data access (`OrderDB`, `ProductDB`, `DBManager`)

<br></br>

```mermaid

classDiagram
    direction TB

    %% ==========================================
    %% 1. PRESENTATION LAYER
    %% ==========================================
    namespace 1_Presentation_Layer {
        class JSP_Views {
            <<Views>>
            cart.jsp
            product.jsp
            staff.jsp
            admin.jsp
            login.jsp
        }
        class CartController {
            +doGet(req, resp)
            +doPost(req, resp)
        }
        class ProductController {
            +doGet(req, resp)
            +doPost(req, resp)
        }
        class StaffController {
            +doGet(req, resp)
            +doPost(req, resp)
        }
        class AdminController {
            +doGet(req, resp)
            +doPost(req, resp)
        }
        class LoginController {
            +doGet(req, resp)
            +doPost(req, resp)
        }
        class OrderController {
            +doGet(req, resp)
            +doPost(req, resp)
        }
    }

    %% ==========================================
    %% 2. DTO LAYER
    %% ==========================================
    namespace 2_DTO_Layer {
        class CartInfo { <<DTO>> }
        class ProductInfo { <<DTO>> }
        class OrderInfo { <<DTO>> }
        class UserInfo { <<DTO>> }
        class CategoryInfo { <<DTO>> }
    }

    %% ==========================================
    %% 3. BUSINESS LOGIC (SERVICES)
    %% ==========================================
    namespace 3_Service_Layer {
        class CartService {
            +addProductToCart()$
            +updateQuantity()$
        }
        class ProductService {
            +getAllProducts()$
            +updateProductStock()$
        }
        class OrderService {
            +placeOrder()$
            +packOrder()$
        }
        class AdminUserService {
            +getAllUsers()$
            +changeUserRole()$
        }
        class CategoryService {
            +getAllCategories()$
        }
        class LoginService {
            +authenticateUser()$
        }
    }

    %% ==========================================
    %% 4. DOMAIN MODELS
    %% ==========================================
    namespace 4_Domain_Model_Layer {
        class Cart {
            +addItem()
            +removeItem()
            +getTotalCartPrice()
        }
        class CartItem {
            -int id
            -int quantity
            +getTotalPrice()
        }
        class Product {
            -int id
            -String name
            -double price
            -int stock
        }
        class Order {
            -int id
            -int userId
            -String status
            -double totalPrice
        }
        class OrderItem {
            -int productId
            -int quantity
            -double unitPrice
        }
        class User {
            -int id
            -String username
            -String role
        }
        class Category {
            -int id
            -String name
        }
    }

    %% ==========================================
    %% 5. PERSISTENCE LAYER (DAOs & DB)
    %% ==========================================
    namespace 5_Persistence_Layer {
        class ProductDB {
            +getAllProducts()$
            +updateProductStock()$
        }
        class OrderDB {
            +placeOrder()$
            +packOrder()$
        }
        class UserDB {
            +getAllUsers()$
            +validateUser()$
        }
        class CategoryDB {
            +getAllCategories()$
        }
        class DBManager {
            +getConnection()$
        }
    }

    %% ==========================================
    %% RELATIONSHIPS
    %% ==========================================

    %% HTTP Flow
    JSP_Views ..> CartController : HTTP Req/Res
    JSP_Views ..> OrderController : HTTP Req/Res
    
    %% --- DTO DATA FLOW (UI -> DTO) ---
    JSP_Views ..> CartInfo : reads & displays
    JSP_Views ..> ProductInfo : reads & displays
    JSP_Views ..> OrderInfo : reads & displays

    %% --- DTO DATA FLOW (Controllers -> DTO) ---
    CartController ..> CartInfo : binds & passes
    ProductController ..> ProductInfo : binds & passes
    StaffController ..> OrderInfo : binds & passes
    AdminController ..> UserInfo : binds & passes
    OrderController ..> OrderInfo : binds & passes

    %% --- DTO DATA FLOW (Services -> DTO) ---
    %% Genom att vända på koden (<..) tvingas DTO-boxen uppåt i layouten!
    CartInfo <.. CartService : consumes & returns
    ProductInfo <.. ProductService : consumes & returns
    OrderInfo <.. OrderService : consumes & returns
    UserInfo <.. AdminUserService : consumes & returns
    %% ---------------------

    %% Controllers call Services
    CartController --> CartService : calls
    ProductController --> ProductService : calls
    StaffController --> OrderService : calls
    StaffController --> ProductService : calls
    AdminController --> AdminUserService : calls
    LoginController --> LoginService : calls
    OrderController --> OrderService : calls

    %% Services map to underlying Domain Models
    CartService ..> Cart : maps to model
    ProductService ..> Product : maps to model
    OrderService ..> Order : maps to model
    AdminUserService ..> User : maps to model

    %% Domain Model relationships
    Cart *-- CartItem : contains
    Order *-- OrderItem : contains
    Product --> Category : belongs to

    %% Persistence implementation (DAOs extending Models)
    Product <|-- ProductDB : extended by
    Order <|-- OrderDB : extended by
    User <|-- UserDB : extended by
    Category <|-- CategoryDB : extended by

    %% DAOs to DB
    ProductDB --> DBManager : executes SQL
    OrderDB --> DBManager : executes SQL
    UserDB --> DBManager : executes SQL
    CategoryDB --> DBManager : executes SQL