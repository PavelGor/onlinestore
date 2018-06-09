package com.gordeev.onlinestore.web.filter;

import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.entity.UserRole;
import com.gordeev.onlinestore.locator.ServiceLocator;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.web.servlet.utils.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserSecurityFilter implements Filter{
    private static final Logger LOG = LoggerFactory.getLogger(UserSecurityFilter.class);
    private SecurityService securityService = (SecurityService) ServiceLocator.getService(SecurityService.class);
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        boolean isAuth = false;

        String token = ServletUtils.getToken(httpServletRequest);

        if (token != null) {
            User user = securityService.getUser(token);
            if (user != null && (user.getRole() == UserRole.USER)) {
                isAuth = true;
                LOG.info("Security: user " + user.getUserName() + " got access rights to " + ((HttpServletRequest) request).getRequestURI());
            }
        }

        if (isAuth) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect("/login");
            LOG.info("Security: user don't have rights for this operation - so rejected");
        }
    }

    @Override
    public void destroy() {

    }
}
