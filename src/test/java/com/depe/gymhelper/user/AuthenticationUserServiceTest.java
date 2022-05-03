package com.depe.gymhelper.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import static com.depe.gymhelper.user.RoleType.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@WithMockUser(username = "testUser",password = "test")
class AuthenticationUserServiceTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationUserService underTest;

    @Test
    void shouldThrowUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> underTest.getLoggedUser());
    }

    @Test
    void shouldReturnLoggedUser() {
        initDatabaseWithTestUser();
        var user = underTest.getLoggedUser();
        assertThat(user.getUsername()).isEqualTo("testUser");
    }

    private User initDatabaseWithTestUser(){
        var user = new User(
                "testUser@gmail.com",
                "testUser",
                passwordEncoder.encode("test"),
                new AthleteInfo(185L, 75.0));
        user.addRole(roleRepository.findByType(USER));
        return userRepository.save(user);
    }
}
