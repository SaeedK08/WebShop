package com.webshop.service;

import com.webshop.dto.LoginDTO;
import com.webshop.dto.UserInfo;
import com.webshop.model.User;

public class LoginService {
    public static UserInfo validateUser(LoginDTO credential) {
        User user = User.validateUser(credential.username(), credential.password());
        if (user != null) {
            return new UserInfo(user.getUsername(), user.getRole(), user.getId());
        }
        return null;
    }
}
