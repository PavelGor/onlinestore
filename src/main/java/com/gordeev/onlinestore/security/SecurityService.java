package com.gordeev.onlinestore.security;

import com.gordeev.onlinestore.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SecurityService {
    private List<Session> sessionList = new ArrayList<>();

    public Session getSession(String token) {
        LocalDateTime time = LocalDateTime.now();
        Iterator<Session> iterator = sessionList.iterator();
        while (iterator.hasNext()) {
            Session session = iterator.next();
            if (session.getToken().equals(token)) {
                if (time.isBefore(session.getExpiredTime())) {
                    return session;
                } else {
                    sessionList.remove(session);
                    return null;
                }
            }
        }
        return null;
    }

    public User getUser(String token) {
        Session session = getSession(token);
        if (session != null) {
            return session.getUser();
        }
        return null;
    }

    public void removeSession(String token) {
        sessionList.remove(getSession(token));
    }

    public void add(Session session) {
        sessionList.add(session);
    }
}
