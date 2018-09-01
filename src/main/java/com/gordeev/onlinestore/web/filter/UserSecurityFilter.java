package com.gordeev.onlinestore.web.filter;

import com.gordeev.onlinestore.entity.UserRole;

public class UserSecurityFilter extends AbstractSecurityFilter{

    public UserSecurityFilter() {
        super();
    }

    public UserRole getUserRole() {
        return UserRole.USER;
    }

}
