package com.gordeev.onlinestore.entity;

public enum UserRole {
    USER("user"), ADMIN("admin");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public static UserRole getByName(String name) {
        for(UserRole userRole : values()){
            if (userRole.name.equalsIgnoreCase(name)){
                return userRole;
            }
        }
        throw new IllegalArgumentException("No user name with name " + name + " found");
    }
}
