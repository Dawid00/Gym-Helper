package com.depe.gymhelper.training;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TrainingDto {

    private final String description;
    @NotNull(message = "Training must have status")
    private final TrainingStatus status;
    @NotNull(message = "Training must have date")
    private final LocalDateTime date;

    public TrainingDto(String description, TrainingStatus status, LocalDateTime date) {
        this.description = description;
        this.status = status;
        this.date = date;
    }

    public TrainingStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }
}