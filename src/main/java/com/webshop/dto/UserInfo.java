package com.webshop.dto;

public class UserInfo {
    private final String username;
    private final String role;
    private final int id;

    public UserInfo(String username, String role, int id) {
        this.username = username;
        this.role = role;
        this.id = id;
    }

    public String getUsername() {return username;}
    public String getRole() {return role;}
    public int getId() {return id;}

    public boolean isAdmin() {return "admin".equalsIgnoreCase(role);}
    public boolean isStaff() {return "staff".equalsIgnoreCase(role);}
}
