package com.gordeev.onlinestore.security;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SecurityService {
    List<Session> sessionList = new ArrayList<>();

    public boolean isValid(String token){
        for (Session session : sessionList) {
            if (session.getToken().equals(token)){

                LocalDateTime time = LocalDateTime.now();
                time = time.plusMinutes(5);

                if (time.isAfter(session.getExpiredTime())){
                    return true;
                } else {
                    sessionList.remove(session);
                }
            }
        }
        return false;
    }

    public Session getSession(String token){
        for (Session session : sessionList) {
            if (session.getToken().equals(token)){
                return session;
            }
        }
        return null;
    }

    public List<Session> getSessionList() {
        return sessionList;
    }
}
