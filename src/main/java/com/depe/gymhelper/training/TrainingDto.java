package com.depe.gymhelper.training;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TrainingDto {

    private String description;
    @NotNull(message = "Training must have status")
    private TrainingStatus status;
    @NotNull(message = "Training must have date")
    private LocalDateTime date;

    public TrainingDto(String description, TrainingStatus status, LocalDateTime date) {
        this.description = description;
        this.status = status;
        this.date = date;
    }

    public TrainingStatus getStatus() {
        return status;
    }

    public void setStatus(TrainingStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}