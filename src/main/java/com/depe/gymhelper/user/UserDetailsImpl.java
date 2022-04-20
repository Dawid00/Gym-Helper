package com.depe.gymhelper.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

class UserDetailsImpl implements UserDetails {

    private final String username;
    private final String password;
    private final Set<GrantedAuthority> authorities;



    UserDetailsImpl(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = authoritiesFromRoles(user.getRoles());
    }

    private static Set<GrantedAuthority> authoritiesFromRoles(Set<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getType().getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
}
