package com.depe.gymhelper.training;

import com.depe.gymhelper.user.UserQueryEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Table(name = "trainings")
class Training {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    @Enumerated(EnumType.STRING)
    private TrainingStatus status;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserQueryEntity user;

    public Training() {
    }

    public Training(String description, TrainingStatus status, LocalDateTime date, UserQueryEntity user) {
        this.description = description;
        this.status = status;
        this.date = date;
        this.user = user;
    }

    UserQueryEntity getUser() {
        return user;
    }


    Long getId() {
        return id;
    }


    String getDescription() {
        return description;
    }


    TrainingStatus getStatus() {
        return status;
    }


    LocalDateTime getDate() {
        return date;
    }


    void update(String description, LocalDateTime date, TrainingStatus status) {
        this.date = date;
        this.description = description;
        this.status = status;
    }

    void changeStatus(String status) {
        switch (status.toUpperCase(Locale.ROOT)) {
            case "PLANNED" -> this.status = TrainingStatus.PLANNED;
            case "DONE" -> this.status = TrainingStatus.DONE;
            default -> throw new IllegalArgumentException("wrong status:" + status);
        }
    }

    void setId(Long id) {
        this.id = id;
    }
}
