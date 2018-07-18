package com.gordeev.onlinestore.web.filter;

import com.gordeev.onlinestore.entity.User;
import com.gordeev.onlinestore.entity.UserRole;
import com.gordeev.onlinestore.context.Context;
import com.gordeev.onlinestore.security.SecurityService;
import com.gordeev.onlinestore.web.servlet.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public abstract class AbstractSecurityFilter implements Filter{
    private static final Logger LOG = LoggerFactory.getLogger(AbstractSecurityFilter.class);
    private SecurityService securityService = Context.getContext(SecurityService.class); //спорный вопрос - или 3 строки в xml-файле???

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        boolean isAuth = false;
        Optional<User> optionalUser;

        String token = ServletUtils.getToken(httpServletRequest);

        if (token != null) {
            optionalUser = securityService.getUser(token);
            if (optionalUser.isPresent() && (optionalUser.get().getRole() == getUserRole())) {
                isAuth = true;
                LOG.info("Security: user {} got access rights to {}", optionalUser.get().getUserName(), httpServletRequest.getRequestURI());
            }
        }

        if (isAuth) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect("/login");
            LOG.info("Security: have no such user session");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }

    public abstract UserRole getUserRole();
}
