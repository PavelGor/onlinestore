package com.gordeev.onlinestore.web.filter;

import com.gordeev.onlinestore.entity.UserRole;

public class UserSecurityFilter extends AbstractSecurityFilter{

    public UserRole getUserRole() {
        return UserRole.USER;
    }

}
