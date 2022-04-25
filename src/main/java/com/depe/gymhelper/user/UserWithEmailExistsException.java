package com.depe.gymhelper.user;

class UserWithEmailExistsException extends RuntimeException {
    UserWithEmailExistsException(String email) {
        super("User with email: %s exists".formatted(email));;
    }
}
