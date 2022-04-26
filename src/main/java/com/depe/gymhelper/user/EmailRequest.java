package com.depe.gymhelper.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EmailRequest {
    @NotNull(message = "Email is mandatory")
    @NotEmpty
    @Email
    private String email;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public EmailRequest(String email) {
        this.email = email;
    }
    public EmailRequest() {
    }
}
