package com.webshop.model;

import com.webshop.dao.UserDB;

import java.awt.image.DataBufferUShort;
import java.util.List;

public class User {
    private String username;
    private String password;
    private int id;
    private String role;

    protected User(String username, String password, int id, String role) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.role = role;
    }

    public static List<User> getAllUsers() {return UserDB.getAllUsers();}

    public static User validateUser(String username, String password) {
        return UserDB.validateUser(username, password);
    }

    public static boolean changeUserPassword(int userId, String newPassword) {
        return UserDB.changeUserPassword(userId, newPassword);
    }
    public static boolean changeUsername(int userId, String newUsername) {
        return UserDB.changeUsername(userId, newUsername);
    }
    public static boolean changeUserRole(int userId, String newRole) {
        return UserDB.changeUserRole(userId, newRole);
    }
    public static boolean deleteUser(int userId) {
        return UserDB.deleteUser(userId);
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public int getId() {return id;}
    public String getRole() {return role;}

    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}

    public boolean isAdmin(int id) {return role.equalsIgnoreCase("admin");}
    public boolean isStaff(int id) {return role.equalsIgnoreCase("staff");}

    // användare försöker logga in -> jsp via dto paktera data -> service -> dto -> auttentisera mot db
    // -> service skapar en session och en cookie -> cookie skickas tillbaka till browsern
}
