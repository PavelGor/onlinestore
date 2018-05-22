package com.gordeev.onlinestore.web.filter;

import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.security.Session;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class AdminSecurityFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig)  {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        SecurityService securityService = (SecurityService) ServiceLocator.getService("securityService");
        boolean isAuth = false;
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token") && securityService.isValid(cookie.getValue())){

                    Session session = securityService.getSession(cookie.getValue());
                    String userName = session.getUser().getUsername();

                    if ("admin".equals(userName)){
                        isAuth = true;
                        break;
                    }



                    //TODO: add check for expired time\date, and if not - delete that session from list

                }
            }
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

