package com.depe.gymhelper.user;

class UserWithUsernameExistsException extends RuntimeException {
    UserWithUsernameExistsException(String username) {
        super("User with email: %s exists".formatted(username));
    }
}
