package com.gordeev.onlinestore.web.filter;

import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserSecurityFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        SecurityService securityService = (SecurityService) ServiceLocator.getService("securityService");
        boolean isAuth = false;
        String userName = securityService.getName((HttpServletRequest) request);

        if (securityService.isValid(request) && "user".equals(userName)) {
            isAuth = true;
        }

        if (isAuth){
            chain.doFilter(request,response);
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {

    }
}
