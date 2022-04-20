package com.depe.gymhelper.user;

public class UserRegisterDto {

    private String email;
    private String username;
    private String password;
    private Long weight;
    private Long height;
    private Integer age;

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

    Long getWeight() {
        return weight;
    }

    void setWeight(Long weight) {
        this.weight = weight;
    }

    Long getHeight() {
        return height;
    }

    void setHeight(Long height) {
        this.height = height;
    }

    Integer getAge() {
        return age;
    }

    void setAge(Integer age) {
        this.age = age;
    }
}
