package com.depe.gymhelper.auth;

import com.depe.gymhelper.user.RegisterUserRequest;
import com.depe.gymhelper.user.UserQueryEntity;
import com.depe.gymhelper.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@WithMockUser(username = "testUser", password = "test")
@ActiveProfiles("test")
@Transactional
class AuthenticationServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService underTest;

    @Test
    void shouldReturnAuthenticationResponse(){
        //given
        initDatabaseWithTestUser();
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
        authenticationRequestDto.setUsername("testUser");
        authenticationRequestDto.setPassword("test");
        //when
        var result = underTest.authenticateUser(authenticationRequestDto);
        //then
        assertThat(result.getType()).isEqualTo("Bearer");
        assertThat(result.getUsername()).isEqualTo("testUser");
        assertThat(result.getEmail()).isEqualTo("email123@gmail.com");
        assertThat(result.getToken().startsWith("eyJhbGciOiJIUzUxMiJ9")).isTrue();
    }

    private UserQueryEntity initDatabaseWithTestUser() {
        var registerRequest = new RegisterUserRequest(
                "email123@gmail.com",
                "testUser",
                "test",
                75.0,
                185L
        );
        Long id = userService.createUser(registerRequest);
        return new UserQueryEntity(id, "testUser");
    }
}
