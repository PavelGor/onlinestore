package com.gordeev.onlinestore.web.filter;

import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.entity.UserRole;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.web.servlet.utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserSecurityFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        SecurityService securityService = (SecurityService) ServiceLocator.getService("securityService");
        boolean isAuth = false;

        String token = ServletUtils.getToken(httpServletRequest);

        if (token != null) {
            User user = securityService.getUser(token);
            if (user != null && (user.getRole() == UserRole.USER)) {
                isAuth = true;
            }
        }

        if (isAuth) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {

    }
}
