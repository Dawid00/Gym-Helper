package com.depe.gymhelper.auth;

class AuthenticationResponseDto {
    private String token;

    public AuthenticationResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    void setToken(String token) {
        this.token = token;
    }
}
