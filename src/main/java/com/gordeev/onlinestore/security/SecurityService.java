package com.gordeev.onlinestore.security;

import com.gordeev.onlinestore.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SecurityService {
    List<Session> sessionList = new ArrayList<>();

    public Session getSession(String token) {
        for (int i = 0; i < sessionList.size(); i++) {
            Session session = sessionList.get(i);
            if (session.getToken().equals(token)) {
                LocalDateTime time = LocalDateTime.now();
                if (time.isBefore(session.getExpiredTime())) {
                    return session;
                } else {
                    sessionList.remove(session);
                }
            }
        }
        return null;
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public User getUser(String token) {
        Session session = getSession(token);
        if (session != null) {
            return session.getUser();
        }
        return null;
    }
}
