package com.depe.gymhelper.training;

import com.depe.gymhelper.user.UserQueryEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "trainings")
class Training {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Training must have status")
    private TrainingStatus status;
    @NotNull(message = "Training must have date")
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserQueryEntity user;

    public Training() {
    }

    UserQueryEntity getUser() {
        return user;
    }

    void setUser(UserQueryEntity user) {
        this.user = user;
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    TrainingStatus getStatus() {
        return status;
    }

    void setStatus(TrainingStatus status) {
        this.status = status;
    }

    LocalDateTime getDate() {
        return date;
    }

    void setDate(LocalDateTime date) {
        this.date = date;
    }
}
