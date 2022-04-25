package com.depe.gymhelper.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RegisterUserRequest {

    @NotNull(message = "Email is mandatory")
    @NotEmpty
    @Email
    private String email;
    @NotNull(message = "username is mandatory")
    @NotBlank
    @Length(min = 5, max = 25, message = "Username must have 5-25 characters")
    private String username;
    @NotNull
    private String password;
    private AthleteInfo info;

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    AthleteInfo getInfo() {
        return info;
    }

    void setInfo(AthleteInfo info) {
        this.info = info;
    }
}