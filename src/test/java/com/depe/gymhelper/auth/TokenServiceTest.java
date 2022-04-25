package com.depe.gymhelper.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TokenServiceTest {

    private TokenService underTest;
    private UserDetails userDetails;
    private JwtConfigurationProperties properties;

    @BeforeEach
    void setUp(){
        properties = mock(JwtConfigurationProperties.class);
        underTest = new TokenService(properties);
        userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Set<GrantedAuthority> roles = new HashSet<>();
                roles.add(new SimpleGrantedAuthority("ROLE_USER"));
                roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                return roles;
            }

            @Override
            public String getPassword() {
                return "testPassword";
            }

            @Override
            public String getUsername() {
                return "testUser";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }

    @Test
    void shouldReturnNotEmptyToken(){
        //when
        when(properties.getSecret()).thenReturn("secret");
        when(properties.getExpirationTimeMs()).thenReturn(86400);
        var resultToken = underTest.createToken(userDetails);
        var resultUsername = underTest.getUsernameFromToken(resultToken);
        boolean isValid = underTest.isValidForUser(resultToken, userDetails);
        //then
        assertThat(resultToken).isNotEmpty();
        assertThat(resultUsername).isEqualTo("testUser");
        assertThat(isValid).isTrue();
    }
}
