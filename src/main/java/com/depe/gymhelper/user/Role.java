package com.depe.gymhelper.user;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="roles")
class Role {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleType type;

    Role(Long id, RoleType type) {
        this.id = id;
        this.type = type;
    }
    Role(RoleType type){
        this.type = type;
    }

    public Role() {
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    RoleType getType() {
        return type;
    }

    void setType(RoleType type) {
        this.type = type;
    }
}
