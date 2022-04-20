package com.depe.gymhelper.user;

enum RoleType {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    final String name;
    RoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
