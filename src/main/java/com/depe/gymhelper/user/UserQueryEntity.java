package com.depe.gymhelper.user;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserQueryEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String username;

    public UserQueryEntity(final Long id, final String username) {
        this.id = id;
        this.username = username;
    }

    public UserQueryEntity() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
