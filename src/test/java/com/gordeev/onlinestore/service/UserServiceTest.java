package com.gordeev.onlinestore.service;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class UserServiceTest {

    @Test
    public void encrypt() {

        UserService userService = new UserService();
            String sole = String.valueOf(UUID.randomUUID());
            System.out.println("Admin sole: "+sole);
        System.out.println("Admin pwd: "+userService.encrypt("admin" + sole));
        String sole2 = String.valueOf(UUID.randomUUID());
        System.out.println("user1 sole: "+sole2);
        System.out.println("user1 pwd: "+userService.encrypt("user" + sole2));
        String sole3 = String.valueOf(UUID.randomUUID());
        System.out.println("user2 sole: "+sole3);
        System.out.println("user2 pwd: "+userService.encrypt("user2" + sole3));
    }
}