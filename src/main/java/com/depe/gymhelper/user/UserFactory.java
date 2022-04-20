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

    User fromUserRegisterDto(UserRegisterDto userRegisterDto) {
        roleRepository.save(new Role(RoleType.USER));
        User user = new User(userRegisterDto.getEmail(), userRegisterDto.getUsername(), passwordEncoder.encode(userRegisterDto.getPassword()),
                new AthleteInfo(userRegisterDto.getHeight(), userRegisterDto.getAge(), userRegisterDto.getWeight()));
        user.addRole(roleRepository.findByType(RoleType.USER));
        return user;
    }
}
