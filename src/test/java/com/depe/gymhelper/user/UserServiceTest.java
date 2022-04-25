package com.depe.gymhelper.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Autowired
    private AuthenticationUserService authenticationUserService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserFactory userFactory;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        authenticationUserService = mock(AuthenticationUserService.class);
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        userFactory = mock(UserFactory.class);
        passwordEncoder = mock(PasswordEncoder.class);
        underTest = new UserService(authenticationUserService, userRepository, roleRepository, userFactory, passwordEncoder);
    }

    @Test
    void shouldReturnListOfUsers() {
        //given
        User user = new User(
                "testEmail@gmail.com",
                "testUsername",
                "testPassword",
                new AthleteInfo(185L, 21, 85L));
        User user2 = new User(
                "testEmail2@gmail.com",
                "testUsername2",
                "testPassword2",
                new AthleteInfo(180L, 22, 75L));
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        //when
        when(userRepository.findAll()).thenReturn(users);
        var result = underTest.getAllUsers();
        //then
        assertThat(result).hasSize(2);
        assertThat(result).contains(user, user2);
    }

    @Test
    void verifySaveAfterCreateUser() {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("testUsername");
        registerUserRequest.setPassword("testPassword");
        registerUserRequest.setEmail("testEmail@gmail.com");
        registerUserRequest.setAge(21);
        registerUserRequest.setHeight(185L);
        registerUserRequest.setWeight(85L);
        User user = new User("testEmail@gmail.com",
                "testUsername",
                "testPassword",
                new AthleteInfo(185L, 21, 85L));
        //when
        when(userRepository.existsByUsername(registerUserRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerUserRequest.getEmail())).thenReturn(false);
        when(userFactory.fromUserRegisterDto(registerUserRequest)).thenReturn(user);
        underTest.createUser(registerUserRequest);
        //then
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldUpdate() {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("newUsername");
        registerUserRequest.setPassword("newPassword");
        registerUserRequest.setEmail("newTestEmail@gmail.com");
        registerUserRequest.setAge(21);
        registerUserRequest.setHeight(185L);
        registerUserRequest.setWeight(85L);
        User user = new User("testEmail@gmail.com",
                "testUsername",
                "testPassword",
                new AthleteInfo(185L, 21, 85L));
        //when
        when(authenticationUserService.getLoggedUser()).thenReturn(new UserQueryEntity(1L, "testUsername"));
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("newPassword");
        underTest.updateUserByUsername("testUsername", registerUserRequest);
        //then
        assertThat(user.getEmail()).isEqualTo("newTestEmail@gmail.com");
        assertThat(user.getPassword()).isEqualTo("newPassword");
        assertThat(user.getUsername()).isEqualTo("newUsername");
    }

    @Test
    void shouldUpdateLogged() {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("newUsername");
        registerUserRequest.setPassword("newPassword");
        registerUserRequest.setEmail("newTestEmail@gmail.com");
        registerUserRequest.setAge(21);
        registerUserRequest.setHeight(185L);
        registerUserRequest.setWeight(85L);
        User user = new User("testEmail@gmail.com",
                "testUsername",
                "testPassword",
                new AthleteInfo(185L, 21, 85L));
        //when
        when(authenticationUserService.getLoggedUser()).thenReturn(new UserQueryEntity(1L, "testUsername"));
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("newPassword");
        underTest.updateLoggedUser(registerUserRequest);
        //then
        assertThat(user.getEmail()).isEqualTo("newTestEmail@gmail.com");
        assertThat(user.getPassword()).isEqualTo("newPassword");
        assertThat(user.getUsername()).isEqualTo("newUsername");
    }


    @Test
    void verifyDeleteDuringDeleteUser() {
        //when
        when(userRepository.existsById(1L)).thenReturn(true);
        underTest.deleteUser(1L);
        //then
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void verifyDeleteDuringDeleteUserByUsername() {
        //when
        when(userRepository.existsByUsername("testUser")).thenReturn(true);
        underTest.deleteUserByUsername("testUser");
        //then
        verify(userRepository, times(1)).deleteByUsername("testUser");
    }

    @Test
    void verifyDeleteLoggedUser() {
        //when
        var user = new User("email@gmail.com", "testuser", "testpassword", new AthleteInfo());
        var userQuery = new UserQueryEntity(1L, "testuser");
        when(authenticationUserService.getLoggedUser()).thenReturn(userQuery);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        underTest.deleteLoggedUser();
        //then
        verify(userRepository, times(1)).deleteByUsername("testuser");
    }


    @Test
    void shouldThrowUserWithEmailExistsException() {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("newUsername");
        registerUserRequest.setPassword("newPassword");
        registerUserRequest.setEmail("newTestEmail@gmail.com");
        registerUserRequest.setAge(21);
        registerUserRequest.setHeight(185L);
        registerUserRequest.setWeight(85L);
        //when
        when(userRepository.existsByUsername(registerUserRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(registerUserRequest.getEmail())).thenReturn(true);
        //then
        assertThrows(UserWithEmailExistsException.class, () -> underTest.createUser(registerUserRequest));
    }

    @Test
    void shouldThrowUserWithUsernameExistsException() {
        //given
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("newUsername");
        registerUserRequest.setPassword("newPassword");
        registerUserRequest.setEmail("newTestEmail@gmail.com");
        registerUserRequest.setAge(21);
        registerUserRequest.setHeight(185L);
        registerUserRequest.setWeight(85L);
        //when
        when(userRepository.existsByUsername(registerUserRequest.getUsername())).thenReturn(true);
        when(userRepository.existsByEmail(registerUserRequest.getEmail())).thenReturn(false);
        //then
        assertThrows(UserWithUsernameExistsException.class, () -> underTest.createUser(registerUserRequest));
    }

    @Test
    void shouldReturnFalseIfEmailAndUsernameExists() {
        //when
        when(userRepository.existsByEmail("email@gmail.com")).thenReturn(true);
        when(userRepository.existsByUsername("user")).thenReturn(true);
        var emailAvailability = underTest.isEmailAvailable("email@gmail.com");
        var usernameAvailability = underTest.isUsernameAvailable("user");
        //then
        assertThat(emailAvailability).isFalse();
        assertThat(usernameAvailability).isFalse();
    }

    @Test
    void shouldReturnTrueIfEmailAndUsernameDoesNotExist() {
        //when
        when(userRepository.existsByEmail("email@gmail.com")).thenReturn(false);
        when(userRepository.existsByUsername("user")).thenReturn(false);
        var emailAvailability = underTest.isEmailAvailable("email@gmail.com");
        var usernameAvailability = underTest.isUsernameAvailable("user");
        //then
        assertThat(emailAvailability).isTrue();
        assertThat(usernameAvailability).isTrue();
    }

    @Test
    void shouldReturnUserQueryEntityByUsername() {
        //given
        var user = new User("email@gmail.com", "testUser", "testPassword", new AthleteInfo());
        //when
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        var result = underTest.getQueryUserByUsername("testUser");
        //then
        assertThat(result.getUsername()).isEqualTo("testUser");
    }

    @Test
    void shouldChangePassword() {
        //given
        var user = new User("email@gmail.com", "testUser", "testPassword", new AthleteInfo());
        //when
        when(authenticationUserService.getLoggedUser()).thenReturn(new UserQueryEntity(1L, "testUser"));
        when(passwordEncoder.encode("newPassword")).thenReturn("newPassword");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        underTest.changePassword(new PasswordRequest("newPassword"));
        //then
        assertThat(user.getPassword()).isEqualTo("newPassword");
    }

    @Test
    void shouldChangeEmail() {
        //given
        var user = new User("email@gmail.com", "testUser", "testPassword", new AthleteInfo());
        //when
        when(authenticationUserService.getLoggedUser()).thenReturn(new UserQueryEntity(1L, "testUser"));
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        underTest.changeEmail(new EmailRequest("newEmail@gmail.com"));
        //then
        assertThat(user.getEmail()).isEqualTo("newEmail@gmail.com");
    }


    @Test
    void shouldReturnUserWithTwoRoles() {
        //given
        Role userRole = new Role(RoleType.USER);
        Role adminRole = new Role(RoleType.ADMIN);
        var user = new User("email@gmail.com", "testUser", "testPassword", new AthleteInfo());
        user.addRole(userRole);
        //when
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(roleRepository.findByType(RoleType.ADMIN)).thenReturn(adminRole);
        underTest.giveAdminRoleToUser("testUser");
        var result = user.getRoles();
        //then
        assertThat(result).hasSize(2).contains(userRole).contains(adminRole);
    }

    @Test
    void shouldReturnUserWithOneRole() {
        //given
        Role userRole = new Role(1L, RoleType.USER);
        Role adminRole = new Role(2L, RoleType.ADMIN);
        var user = new User("email@gmail.com", "testUser", "testPassword", new AthleteInfo());
        user.addRole(userRole);
        user.addRole(adminRole);
        //when
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(roleRepository.findByType(RoleType.ADMIN)).thenReturn(adminRole);
        underTest.takeAdminRoleFromUser("testUser");
        var result = user.getRoles();
        //then
        assertThat(result).hasSize(1).contains(userRole);
    }



}

//    @Transactional
//    public void updateLoggedUser(RegisterUserRequest registerUserRequest){
//        var user = getLoggedUser();
//        registerUserRequest.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
//        user.updateUserByRequest(registerUserRequest);
//    }
//
//    @Transactional
//    public void updateUserByUsername(String username, RegisterUserRequest registerUserRequest) {
//        var user = getUserByUsername(username);
//        registerUserRequest.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
//        user.updateUserByRequest(registerUserRequest);
//    }
//


