package com.gordeev.onlinestore.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig)  {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        boolean isAuth = false;
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")){
                    isAuth = true;
                    break;
                }
            }
        }

        if (isAuth || "/login".equals(((HttpServletRequest) request).getRequestURI())){
            chain.doFilter(request,response);
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {

    }
}

