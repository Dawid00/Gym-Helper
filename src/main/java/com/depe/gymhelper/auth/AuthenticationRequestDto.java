package com.depe.gymhelper.auth;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AuthenticationRequestDto {
    @NotNull(message = "username is mandatory")
    @NotBlank
    @Length(min = 5, max = 25, message = "Username must have 5-25 characters")
    private String username;
    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}