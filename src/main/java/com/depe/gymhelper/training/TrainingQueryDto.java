package com.depe.gymhelper.training;

import java.time.LocalDateTime;

public class TrainingQueryDto {

    private Long id;
    private String description;
    private TrainingStatus status;
    private LocalDateTime date;

    public TrainingQueryDto(Long id, String description, TrainingStatus status, LocalDateTime date) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TrainingStatus getStatus() {
        return status;
    }

    public void setStatus(TrainingStatus status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
