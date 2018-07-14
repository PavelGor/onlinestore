package com.gordeev.onlinestore.security;

import com.gordeev.onlinestore.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SecurityService {
    private static final int SESSION_MAX_LIFE_TIME = 300;
    private List<Session> sessionList = new ArrayList<>();

    public Optional<Session> getSession(String token) {
        LocalDateTime currentTime = LocalDateTime.now();
        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                if (currentTime.isBefore(session.getExpireTime())) {
                    return Optional.of(session);
                } else {
                    sessionList.remove(session);
                    break;
                }
            }
        }
        return Optional.empty();
    }

    public Optional<User> getUser(String token) {
        Optional<Session> optionalSession = getSession(token);
        return optionalSession.map(Session::getUser);
    }

    public void removeSession(String token) {
        sessionList.remove(new Session(token));
    }

    public void add(Session session) {
        sessionList.add(session);
    }

    public Optional<Session> getSession(User user) {
        LocalDateTime time = LocalDateTime.now();
        for (Session session : sessionList) {
            if (session.getUser().equals(user)) {
                if (time.isBefore(session.getExpireTime())) {
                    return Optional.of(session);
                } else {
                    sessionList.remove(session);
                    break;
                }
            }
        }
        return Optional.empty();
    }

    public int getSessionMaxLifeTime() {
        return SESSION_MAX_LIFE_TIME;
    }
}
