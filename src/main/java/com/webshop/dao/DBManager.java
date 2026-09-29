package com.webshop.dao;

import java.sql.*;

public class DBManager {
    private static DBManager instance = null;
    private Connection conn = null;

    private static DBManager getInstance() {
        if (instance == null) {instance = new DBManager();}
        return instance;

    }
    private DBManager() {
        String url = "jdbc:mysql://localhost:3306/webshop";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "root");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return getInstance().conn;
    }

}
