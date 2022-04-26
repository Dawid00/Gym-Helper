package com.depe.gymhelper.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class UserFactory {
    private final PasswordEncoder passwordEncoder;
    UserFactory(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    User fromUserRegisterDto(RegisterUserRequest registerUserRequest) {
        return new User(registerUserRequest.getEmail(), registerUserRequest.getUsername(), passwordEncoder.encode(registerUserRequest.getPassword()),
                new AthleteInfo(registerUserRequest.getHeight(), registerUserRequest.getWeight()));
    }
}