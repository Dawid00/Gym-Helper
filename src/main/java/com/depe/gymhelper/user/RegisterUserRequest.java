package com.depe.gymhelper.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

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
    @NotNull()
    @Min(value = 1, message = "Its not possible")
    @Max(value = 1000,  message = "Its not possible")
    private Long weight;
    @NotNull
    @Min(value = 1, message = "Its not possible")
    @Max(value = 250,  message = "Its not possible")
    private Long height;

    public RegisterUserRequest(String email, String username, String password, Long weight, Long height) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.weight = weight;
        this.height = height;
    }

    public RegisterUserRequest() {
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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