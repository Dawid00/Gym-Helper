package com.depe.gymhelper.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PasswordRequest {

    @NotNull
    @NotBlank(message = "Password is mandatory")
    private String password;

    public PasswordRequest(String password) {
        this.password = password;
    }
    public PasswordRequest(){}
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
