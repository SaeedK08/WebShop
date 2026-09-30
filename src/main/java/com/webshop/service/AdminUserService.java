package com.webshop.service;

import com.webshop.dto.UserInfo;
import com.webshop.model.User;

import java.util.ArrayList;
import java.util.List;

public class AdminUserService {
    public static List<UserInfo> getAllUsers() {
        List<User> users = User.getAllUsers();
        if (users == null) return new ArrayList<>();
        List<UserInfo> usersInfo = new ArrayList<>();
        for (User user : users) {
            usersInfo.add(new UserInfo(user.getUsername(), user.getRole(), user.getId()));
        }

        return usersInfo;
    }
    public static boolean changeUserPassword(int userId, String newPassword) {
        return User.changeUserPassword(userId, newPassword);
    }

    public static boolean changeUsername(int userId, String newUsername) {
        return User.changeUsername(userId, newUsername);
    }

    public static boolean changeUserRole(int userId, String newRole) {
        if (!newRole.trim().equalsIgnoreCase("admin") && !newRole.trim().equalsIgnoreCase("staff")
            && !newRole.trim().equalsIgnoreCase("customer"))   return false;

        return User.changeUserRole(userId, newRole);
    }

    public static boolean deleteUser(int targetUserId, int adminUserId) {
        if (targetUserId == adminUserId)
            return false;
        return User.deleteUser(targetUserId);
    }
}
