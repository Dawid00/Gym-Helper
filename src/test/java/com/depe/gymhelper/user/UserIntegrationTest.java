package com.depe.gymhelper.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@WithMockUser(username = "testUser", password = "test")
class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService underTest;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationUserService authenticationUserService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Test
    void shouldChangeEmail(){
        var user = initDatabaseWithTestUser();
        //when
        underTest.changeEmail(new EmailRequest("new@gmail.com"));
        //then
        assertThat(user.getEmail()).isEqualTo("new@gmail.com");
    }


    @ParameterizedTest
    @MethodSource("provideUsernames")
    void shouldCheckUsername(String username, boolean expected){
        //given
        initDatabaseWithTestUser();
        //when
        var result = underTest.isUsernameAvailable(username);
        //then
        assertThat(result).isEqualTo(expected);
    }


    @ParameterizedTest
    @MethodSource("provideEmails")
    void shouldCheckEmail(String email, boolean expected){
        //given
        initDatabaseWithTestUser();
        //when
        var result = underTest.isEmailAvailable(email);
        //then
        assertThat(result).isEqualTo(expected);
    }

    private User initDatabaseWithTestUser(){
        return userRepository.save(
                new User(
                        "testUser@gmail.com",
                        "testUser",
                        passwordEncoder.encode("test"),
                        new AthleteInfo(185L, 75.0)
                )
        );
    }

    private static Stream<Arguments> provideUsernames(){
        return Stream.of(
                Arguments.of("testUser",false),
                Arguments.of("newUsername",true)
        );
    }

    private static Stream<Arguments> provideEmails(){
        return Stream.of(
                Arguments.of("testUser@gmail.com",false),
                Arguments.of("new@email.com",true)
        );
    }


}
