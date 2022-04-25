package com.depe.gymhelper.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class UserFactory {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    UserFactory(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    User fromUserRegisterDto(RegisterUserRequest registerUserRequest) {
        User user = new User(registerUserRequest.getEmail(), registerUserRequest.getUsername(), passwordEncoder.encode(registerUserRequest.getPassword()),
                new AthleteInfo(registerUserRequest.getHeight(), registerUserRequest.getWeight()));
        user.addRole(roleRepository.findByType(RoleType.USER));
        return user;
    }
}