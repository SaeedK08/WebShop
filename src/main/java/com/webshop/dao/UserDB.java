package com.webshop.dao;

import com.webshop.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDB extends User {
    private UserDB(String username, String password, int id, String role) {
        super(username, password, id, role);
    }

    public static List<User> getAllUsers() {
        Connection conn = DBManager.getConnection();
        String selectQuery = "SELECT * FROM User";
        try (PreparedStatement ps = conn.prepareStatement(selectQuery);
            ResultSet rs = ps.executeQuery()) {
            List<User> users = new ArrayList<>();
            while(rs.next()) {
                users.add(new UserDB(rs.getString("username"),
                                    rs.getString("password"),
                                    rs.getInt("id"),
                                    rs.getString("role")));
            }
            return users;
        } catch (Exception e) {e.printStackTrace(); return null;}
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

    public static boolean changeUserPassword(int userId, String newPassword) {
        Connection conn = DBManager.getConnection();
        String sql = "UPDATE User SET password = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setString(1, newPassword);
            ps.setInt(2, userId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated != 1) {
                throw new SQLException("Updating user password failed");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changeUsername(int userId, String newUsername) {
        Connection conn = DBManager.getConnection();
        String sql = "UPDATE User SET username = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setString(1, newUsername);
            ps.setInt(2, userId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated != 1) {
                throw new SQLException("Updating username failed");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changeUserRole(int userId, String newRole) {
        Connection conn = DBManager.getConnection();
        String sql = "UPDATE User SET role = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setString(1, newRole);
            ps.setInt(2, userId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated != 1) {
                throw new SQLException("Updating user role failed");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteUser(int userId) {
        Connection conn = DBManager.getConnection();
        String sql = "DELETE FROM User WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setInt(1, userId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated != 1) {
                throw new SQLException("Updating a user password failed");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
