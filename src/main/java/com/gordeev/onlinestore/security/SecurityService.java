package com.gordeev.onlinestore.security;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SecurityService {
    List<Session> sessionList = new ArrayList<>();

    public boolean isValid(ServletRequest request) {
        String tokenFromRequest = getToken((HttpServletRequest) request);

        Session session = getSession(request);

        if (session != null && tokenFromRequest != null) {
            if (session.getToken().equals(tokenFromRequest)) {

                LocalDateTime time = LocalDateTime.now();
                time = time.plusMinutes(5);

                if (time.isAfter(session.getExpiredTime())) {
                    return true;
                } else {
                    sessionList.remove(session);
                }
            }
        }
        return false;
    }

    public Session getSession(ServletRequest request) {
        String token = getToken((HttpServletRequest) request);
        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                return session;
            }
        }
        return null;
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public String getName(HttpServletRequest request) {

        Session session = getSession(request);
        if (session != null) {
            return session.getUser().getUsername();
        }
        return null;
    }

    private String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
