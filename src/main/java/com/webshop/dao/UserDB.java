package com.webshop.dao;

import com.webshop.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDB extends User {
    private UserDB(String username, String password, int id, String role) {
        super(username, password, id, role);
    }

    public static User validateUser(String username, String password) {
        Connection conn = DBManager.getConnection();
        String selectQuery = "SELECT * FROM User WHERE username=? AND password=?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet rs = preparedStatement.executeQuery();) {
                if (rs.next()) {
                    return new UserDB(rs.getString("username"),
                                    rs.getString("password"),
                                    rs.getInt("id"),
                                    rs.getString("role"));
                }
            }

        } catch(Exception e) {e.printStackTrace();}
        return null;
    }
}
