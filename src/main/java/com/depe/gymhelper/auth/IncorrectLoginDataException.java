package com.depe.gymhelper.auth;

class IncorrectLoginDataException extends RuntimeException {
    IncorrectLoginDataException(String message) {
        super(message);
    }
}
