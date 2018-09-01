package com.gordeev.onlinestore.web.filter;

import com.gordeev.onlinestore.entity.UserRole;

public class AdminSecurityFilter extends AbstractSecurityFilter {

    public AdminSecurityFilter() {
        super();
    }

    public UserRole getUserRole() {
        return UserRole.ADMIN;
    }

}

