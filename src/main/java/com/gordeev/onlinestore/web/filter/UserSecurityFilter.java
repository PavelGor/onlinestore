package com.gordeev.onlinestore.web.filter;

import com.gordeev.onlinestore.entity.UserRole;
import com.gordeev.onlinestore.security.SecurityService;

public class UserSecurityFilter extends AbstractSecurityFilter{

    public UserSecurityFilter(SecurityService securityService) {
        super(securityService);
    }

    public UserRole getUserRole() {
        return UserRole.USER;
    }

}
