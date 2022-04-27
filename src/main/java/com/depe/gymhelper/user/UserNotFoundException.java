package com.depe.gymhelper.user;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String username) {
        super("User with " + username + " not found");
    }
}
