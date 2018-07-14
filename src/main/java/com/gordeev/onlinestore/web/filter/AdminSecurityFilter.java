package com.gordeev.onlinestore.web.filter;

import com.gordeev.onlinestore.entity.UserRole;

public class AdminSecurityFilter extends AbstractSecurityFilter {

    public UserRole getUserRole() {
        return UserRole.ADMIN;
    }

}

