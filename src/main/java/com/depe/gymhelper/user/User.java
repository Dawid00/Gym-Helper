package com.depe.gymhelper.user;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
class User {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String username;
    private String password;
    @Embedded
    private AthleteInfo athleteInfo;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    protected User() {
    }

    User(String email, String username, String password, AthleteInfo athleteInfo) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.athleteInfo = athleteInfo;
    }

    Set<Role> getRoles() {
        return roles;
    }

    void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

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

    AthleteInfo getAthleteInfo() {
        return athleteInfo;
    }

    void setAthleteInfo(AthleteInfo athleteInfo) {
        this.athleteInfo = athleteInfo;
    }

    void addRole(Role role){
        if(roles.contains(role)){
            return;
        }
        roles.add(role);
    }

    void removeRole(Role role) {
        if(roles.contains(role)){
            roles.remove(role);
        }
    }

    void updateUserByRequest(RegisterUserRequest registerUserRequest) {
        this.email = registerUserRequest.getEmail();
        this.username = registerUserRequest.getUsername();
        this.password = registerUserRequest.getPassword();
        this.athleteInfo = new AthleteInfo(registerUserRequest.getHeight(), registerUserRequest.getAge(), registerUserRequest.getWeight());
    }
}
