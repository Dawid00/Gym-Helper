package com.depe.gymhelper.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.depe.gymhelper.user.RoleType.ADMIN;
import static com.depe.gymhelper.user.RoleType.USER;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@WithMockUser(username = "testUser", password = "test")
class UserIntegrationTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService underTest;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ParameterizedTest
    @MethodSource("provideUsernames")
    void shouldCheckUsername(String username, boolean expected) {
        //given
        initDatabaseWithTestUser();
        //when
        var result = underTest.isUsernameAvailable(username);
        //then
        assertThat(result).isEqualTo(expected);
    }


    @ParameterizedTest
    @MethodSource("provideEmails")
    void shouldCheckEmail(String email, boolean expected) {
        //given
        initDatabaseWithTestUser();
        //when
        var result = underTest.isEmailAvailable(email);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldChangeEmail() {
        var user = initDatabaseWithTestUser();
        //when
        underTest.changeEmail(new EmailRequest("new@gmail.com"));
        //then
        assertThat(user.getEmail()).isEqualTo("new@gmail.com");
    }

    @Test
    void shouldChangePassword() {
        //when
        var user = initDatabaseWithTestUser();
        var oldPassword = user.getPassword();
        underTest.changePassword(new PasswordRequest("newPassword"));
        //then
        assertThat(user.getPassword()).isNotEqualTo(oldPassword);
    }

    @Test
    void shouldCreateUser() {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("user123");
        registerUserRequest.setPassword("password");
        registerUserRequest.setEmail("user123@gmail.com");
        registerUserRequest.setHeight(185L);
        registerUserRequest.setWeight(75.0);
        //when
        Long id = underTest.createUser(registerUserRequest);
        //then
        assertThat(id).isNotNull();
        assertThat(userRepository.findById(id).get().getEmail()).isEqualTo("user123@gmail.com");
        assertThat(userRepository.findById(id).get().getUsername()).isEqualTo("user123");
    }

    @Test
    void shouldDeleteUserById() {
        //given
        Long id = userRepository.save(new User(
                "depe@gmail.com",
                "depe123",
                "password",
                new AthleteInfo(185L, 85.0)
        )).getId();
        //when
        underTest.deleteUser(id);
        //then
        assertThat(userRepository.findById(id)).isEqualTo(Optional.empty());
        assertThat(userRepository.findAll()).hasSize(0);
    }

    @Test
    void shouldDeleteLoggedUser() {
        //given
        initDatabaseWithTestUser();
        //when
        assertThat(userRepository.findAll()).hasSize(1);
        underTest.deleteLoggedUser();
        //then
        assertThat(userRepository.findAll()).hasSize(0);
    }

    @Test
    void shouldGiveAdminRoleToUser() {
        //given
        var user = initDatabaseWithTestUser();
        //when
        underTest.giveAdminRoleToUser(user.getUsername());
        var result = user.getRoles();
        var nameOfRole = result.stream()
                .map(Role::getType)
                .map(RoleType::getName)
                .collect(Collectors.toSet());
        //then
        assertThat(result).hasSize(2);
        assertThat(nameOfRole.contains("ROLE_USER")).isTrue();
        assertThat(nameOfRole.contains("ROLE_ADMIN")).isTrue();
    }

    @Test
    void shouldTakeAdminRoleToUser() {
        //given
        var user = initDatabaseWithTestUser();
        user.addRole(roleRepository.findByType(ADMIN));
        //when
        underTest.takeAdminRoleFromUser(user.getUsername());
        var result = user.getRoles();
        var nameOfRole = result.stream()
                .map(Role::getType)
                .map(RoleType::getName)
                .collect(Collectors.toSet());
        //then
        assertThat(result).hasSize(1);
        assertThat(nameOfRole.contains("ROLE_USER")).isTrue();
    }

    @Test
    void shouldGetQueryUserByUsername() {
        //given
        var expectedId = initDatabaseWithTestUser().getId();
        //when
        var result = underTest.getQueryUserByUsername("testUser");
        //then
        assertThat(result.getUsername()).isEqualTo("testUser");
        assertThat(result.getId()).isEqualTo(expectedId);
    }

    @Test
    void shouldGetAllUsers() {
        var testUser = initDatabaseWithTestUser();
        //when
        var results = underTest.getAllUsers();
        //then
        assertThat(results).hasSize(1);
        assertThat(results.contains(testUser)).isTrue();
    }

    @Test
    void shouldUpdateUserByUsername() {
        //given
        initDatabaseWithTestUser();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("user123");
        registerUserRequest.setPassword("password");
        registerUserRequest.setEmail("user123@gmail.com");
        registerUserRequest.setHeight(195L);
        registerUserRequest.setWeight(89.0);
        var user = userRepository.findAll().get(0);
        //when
        underTest.updateUserByUsername("testUser", registerUserRequest);
        //then
        assertThat(user.getUsername()).isEqualTo("user123");
        assertThat(user.getAthleteInfo().getHeight()).isEqualTo(195L);
        assertThat(user.getAthleteInfo().getWeight()).isEqualTo(89.0);
    }

    @Test
    void shouldUpdateLoggedUser() {
        //given
        initDatabaseWithTestUser();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("user123");
        registerUserRequest.setPassword("password");
        registerUserRequest.setEmail("user123@gmail.com");
        registerUserRequest.setHeight(195L);
        registerUserRequest.setWeight(89.0);
        var user = userRepository.findAll().get(0);
        //when
        underTest.updateLoggedUser(registerUserRequest);
        //then
        assertThat(user.getUsername()).isEqualTo("user123");
        assertThat(user.getAthleteInfo().getHeight()).isEqualTo(195L);
        assertThat(user.getAthleteInfo().getWeight()).isEqualTo(89.0);

    }

    @Test
    void shouldDeleteUserByUsername() {
        //given
        Long id = userRepository.save(new User(
                "depe@gmail.com",
                "depe123",
                "password",
                new AthleteInfo(185L, 85.0)
        )).getId();
        //when
        underTest.deleteUserByUsername("depe123");
        //then
        assertThat(userRepository.findById(id)).isEqualTo(Optional.empty());
        assertThat(userRepository.findAll()).hasSize(0);
    }


    private User initDatabaseWithTestUser() {

        if (userRepository.findByUsername("testUser").isEmpty()) {
            var user = new User(
                    "testUser@gmail.com",
                    "testUser",
                    passwordEncoder.encode("test"),
                    new AthleteInfo(185L, 75.0));
            user.addRole(roleRepository.findByType(USER));
            userRepository.findAll();
            return userRepository.save(user);
        }
        else{
            return userRepository.findByUsername("testUser").get();

        }
    }

    private static Stream<Arguments> provideUsernames() {
        return Stream.of(
                Arguments.of("testUser", false),
                Arguments.of("newUsername", true)
        );
    }

    private static Stream<Arguments> provideEmails() {
        return Stream.of(
                Arguments.of("testUser@gmail.com", false),
                Arguments.of("new@email.com", true)
        );
    }
}
